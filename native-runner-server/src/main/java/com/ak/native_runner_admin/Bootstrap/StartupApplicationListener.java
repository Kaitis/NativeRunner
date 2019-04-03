package com.ak.native_runner_admin.Bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.ak.native_runner_admin.repository.ProcessGroupRepository;
import com.ak.native_runner_admin.repository.ProcessRepository;
import com.ak.native_runner_admin.scheduler.SimpleTaskScheduler;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  private ProcessRepository repository;
  private ProcessGroupRepository groupRepository;
  private SimpleTaskScheduler scheduler;

  @Autowired
  public StartupApplicationListener(ProcessRepository repository,
    ProcessGroupRepository groupRepository,
    SimpleTaskScheduler scheduler) {
    this.repository = repository;
    this.groupRepository = groupRepository;
    this.scheduler = scheduler;
  }

  @Override public void onApplicationEvent(ContextRefreshedEvent event) {
    repository.findAll().forEach(process -> {
      if (process.isStandalone() && null != process.getCron())
        scheduler.schedule(process);
    });
    groupRepository.findAll()
      .forEach(scheduler::schedule);

  }
}