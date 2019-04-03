package com.ak.process_launcher.service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.ak.process_launcher.runners.ProcessGroupRunner;
import com.ak.process_launcher.runners.ProcessRunner;
import com.ak.process_launcher.web.to.ProcessGroupHistoryTO;
import com.ak.process_launcher.web.to.ProcessGroupTO;
import com.ak.process_launcher.web.to.ProcessHistoryTO;
import com.ak.process_launcher.web.to.ProcessTO;

@Service
public class ProcessServiceImpl implements ProcessService {

  private ApplicationContext context;

  @Autowired
  public ProcessServiceImpl(ApplicationContext context) {
    this.context = context;
  }

  @Override
  public ProcessHistoryTO start(ProcessTO process) throws ExecutionException, InterruptedException{
    ProcessRunner processRunner = context.getAutowireCapableBeanFactory().createBean(ProcessRunner.class);
    processRunner.withProcess(process);
    return Executors.newSingleThreadExecutor().submit(processRunner).get();

  }

  @Override
  public ProcessHistoryTO stop(ProcessTO process) throws Exception {
    ProcessRunner runner = context.getBeansOfType(ProcessRunner.class).values()
      .stream()
      .filter(r -> Objects.equals(r.getProcess().getId(), process.getId()))
      .findFirst().orElseThrow(Exception::new);

    return runner.stop();
  }

  @Override
  public ProcessGroupHistoryTO startGroup(ProcessGroupTO processGroup) throws ExecutionException, InterruptedException {
    ProcessGroupRunner processGroupRunner = context.getAutowireCapableBeanFactory().createBean(ProcessGroupRunner.class);
    processGroupRunner.withChain(processGroup);
    return Executors.newSingleThreadExecutor().submit(processGroupRunner).get();
  }

  @Override
  public ProcessGroupHistoryTO stopGroup(ProcessGroupTO processGroup) throws Exception {
    ProcessGroupRunner groupRunner = context.getBeansOfType(ProcessGroupRunner.class).values()
      .stream()
      .filter(r -> r.getChain().getName().equalsIgnoreCase(processGroup.getName()))
      .findFirst().orElseThrow(Exception::new);

    return groupRunner.stop();
  }

}
