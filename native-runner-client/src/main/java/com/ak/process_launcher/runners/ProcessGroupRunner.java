package com.ak.process_launcher.runners;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.ak.process_launcher.web.to.ChainProcessTO;
import com.ak.process_launcher.web.to.EProcessStatus;
import com.ak.process_launcher.web.to.ProcessGroupHistoryTO;
import com.ak.process_launcher.web.to.ProcessGroupTO;
import com.ak.process_launcher.web.to.ProcessHistoryTO;

/**
 *
 */
@Component
@Scope("prototype")
public class ProcessGroupRunner implements Callable<ProcessGroupHistoryTO>{

  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessGroupRunner.class);
  private ProcessGroupTO chain;
  private ProcessGroupHistoryTO groupHistory;
  private List<ProcessHistoryTO> results;
  private ApplicationContext context;
  private List<ProcessRunner> runners;

  @Value("${runner.shouldShowLogs}")
  private Boolean shouldShowLogs;


  @Autowired
  public ProcessGroupRunner(ApplicationContext context) {
    this.context = context;
  }

  public ProcessGroupRunner withChain(ProcessGroupTO group){
    this.chain = group;
    return this;
  }

  public ProcessGroupRunner shouldShowLogs(Boolean shouldShowLogs){
    this.shouldShowLogs = shouldShowLogs;
    return this;
  }

  @Override
  public ProcessGroupHistoryTO call() {
    runners = new ArrayList<>();
    chain.setStatus(EProcessStatus.RUNNING);
    groupHistory = new ProcessGroupHistoryTO();
    groupHistory.setProcessGroup(chain.getName());
    groupHistory.setProcessGroupId(chain.getId());
    groupHistory.setStartTime(LocalDateTime.now());

    results = new ArrayList<>();

    LOGGER.info("Starting process chain '{}' ", chain);

    for(ChainProcessTO cp :chain.getProcesses() ) {
      LOGGER.info("Creating Process runner with process {} --> step: {}",  cp.getProcessTO().getName(), cp.getStep());
      runners.add(context.getAutowireCapableBeanFactory().createBean(ProcessRunner.class).withProcess(
        cp.getProcessTO()).shouldShowLogs(shouldShowLogs));
    }
    for (ProcessRunner runner : runners) {
      results.add(runner.call());
    }
    groupHistory.setProcessHistory(results);
    groupHistory.setResult(EProcessStatus.COMPLETED);

    for (ProcessHistoryTO r : results){
      if (r.getResult() == EProcessStatus.FAILED)
        groupHistory.setResult(EProcessStatus.FAILED);
      else if (r.getResult() == EProcessStatus.STOPPED)
        groupHistory.setResult(EProcessStatus.STOPPED);
    }

    LOGGER.info("Completed process chain '{}' with groupHistory {}", chain.getName(), groupHistory.getResult());

    return groupHistory;
  }

  public ProcessGroupHistoryTO stop(){
    runners.forEach(r -> results.add(r.stop()));
    groupHistory.setProcessHistory(results);
    groupHistory.setResult(EProcessStatus.STOPPED);
    LOGGER.info("Stopped process chain '{}' ", chain.getName());
    return groupHistory;
  }

  public ProcessGroupTO getChain() {
    return chain;
  }

  public void setChain(ProcessGroupTO chain) {
    this.chain = chain;
  }
}
