package com.ak.native_runner_admin.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import com.ak.native_runner_admin.domain.AbstractProcess;
import com.ak.native_runner_admin.domain.Process;
import com.ak.native_runner_admin.domain.ProcessGroup;

@Component
public class SimpleTaskScheduler {

  private final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskScheduler.class);

  private final TaskScheduler taskScheduler;
  private final ApplicationContext context;
  private Map<String, ScheduledFuture<?>> activeScheduleMap = new HashMap<>();

  @Autowired
  public SimpleTaskScheduler(TaskScheduler taskScheduler, ApplicationContext context) {
    this.taskScheduler = taskScheduler;
    this.context = context;
  }

  public void schedule(Process process){
    LOGGER.info("Attempting to schedule process: {}", process);
    ScheduledFuture<?> future = taskScheduler.schedule(context.getAutowireCapableBeanFactory()
      .createBean(RemoteProcessLauncher.class)
      .withProcess(process), new CronTrigger(process.getCron()));
    activeScheduleMap.put(process.getName(), future);
  }

  public void schedule(ProcessGroup processGroup){
    LOGGER.info("Attempting to schedule chain: {}", processGroup);
    ScheduledFuture<?> future = taskScheduler.schedule(context.getAutowireCapableBeanFactory()
      .createBean(RemoteProcessLauncher.class)
      .withProcess(processGroup), new CronTrigger(processGroup.getCron()));
    activeScheduleMap.put(processGroup.getName(), future);
  }

  public void remove(AbstractProcess process) {

    if(activeScheduleMap.containsKey(process.getName())) {
      activeScheduleMap.get(process.getName()).cancel(true);
      activeScheduleMap.remove(process.getName());
    }else
      LOGGER.debug("Process not found in schedule map: {}", process.getName());
  }

  public Map<String, ScheduledFuture<?>> getActiveScheduleMap() {
    return activeScheduleMap;
  }
}

