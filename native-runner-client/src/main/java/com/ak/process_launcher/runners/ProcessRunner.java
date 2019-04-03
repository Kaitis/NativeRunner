package com.ak.process_launcher.runners;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.ak.process_launcher.web.to.EProcessStatus;
import com.ak.process_launcher.web.to.ProcessHistoryTO;
import com.ak.process_launcher.web.to.ProcessTO;

@Component
@Scope("prototype")
public class ProcessRunner implements Callable<ProcessHistoryTO>{

  static final Logger LOGGER = LoggerFactory.getLogger(ProcessRunner.class);

  private ProcessTO process;
  private ProcessHistoryTO instance;
  private java.lang.Process osProcess;
  private StreamGobbler streamGobbler;

  @Value("${runner.shouldShowLogs}")
  private Boolean shouldShowLogs;


  public ProcessRunner withProcess(ProcessTO process){
    this.process = process;
    return this;
  }

  public ProcessRunner shouldShowLogs(Boolean shouldShowLogs){
    this.shouldShowLogs = shouldShowLogs;
    return this;
  }

  @Override
  public ProcessHistoryTO call() {

    LOGGER.info("Starting process '{}' with command: {}", process.getName(), process.getCommand());

    process.setStatus(EProcessStatus.RUNNING);
    instance = new ProcessHistoryTO();
    instance.setProcess(process.getName());
    instance.setProcessID(process.getId());
    instance.setStartTime(LocalDateTime.now());

    ProcessBuilder builder = new ProcessBuilder();
    builder.directory(new File(process.getDirectory()));
    builder.command(process.getCommand().split(" "));

    try {
      osProcess = builder.start();

      if (shouldShowLogs){
        streamGobbler = new StreamGobbler(osProcess.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
      }

      int exitCode = osProcess.waitFor();

      if (exitCode == 0)
        instance.setResult(EProcessStatus.COMPLETED);

      else
        instance.setResult(EProcessStatus.FAILED);

    }
    catch (Exception e) {
      instance.setResult(EProcessStatus.FAILED);
      LOGGER.error("Process: \"{}\" threw exception: {}", process.getName(), e.toString());
    }
    finally {
      process.setStatus(instance.getResult());
      instance.setStopTime(LocalDateTime.now());
      LOGGER.info("Completed process '{}' with result {}", process.getName(), process.getStatus());

    }

    return instance;

  }

  public ProcessTO getProcess() {
    return process;
  }

  public ProcessHistoryTO stop() {
    if (shouldShowLogs)
      streamGobbler.shutdown();
    process.setStatus(EProcessStatus.STOPPED);
    instance.setStopTime(LocalDateTime.now());
    instance.setResult(EProcessStatus.STOPPED);
    osProcess.destroy();
    return instance;
  }


  private class StreamGobbler implements Runnable {
    private volatile boolean shutdown;
    private InputStream inputStream;
    private Consumer<String> consumer;

    StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
      this.inputStream = inputStream;
      this.consumer = consumer;
    }

    @Override
    public void run() {
      while (!shutdown) {
        new BufferedReader(new InputStreamReader(inputStream)).lines()
          .forEach(consumer);
      }
    }

    void shutdown() {
      shutdown = true;
    }
  }
}
