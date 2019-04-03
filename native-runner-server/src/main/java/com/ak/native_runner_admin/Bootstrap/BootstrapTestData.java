package com.ak.native_runner_admin.Bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.ak.native_runner_admin.domain.EProcessPriority;
import com.ak.native_runner_admin.domain.Process;
import com.ak.native_runner_admin.domain.ProcessGroup;
import com.ak.native_runner_admin.repository.ProcessGroupRepository;
import com.ak.native_runner_admin.repository.ProcessRepository;
import com.ak.native_runner_admin.scheduler.SimpleTaskScheduler;

@Component
@Profile("dev")
public class BootstrapTestData implements CommandLineRunner {

  private SimpleTaskScheduler taskScheduler;
  private ProcessRepository processRepository;
  private ProcessGroupRepository groupRepository;


  private boolean isWindows = System.getProperty("os.name")
    .toLowerCase().startsWith("windows");

  private String command;


  @Autowired
  public BootstrapTestData(SimpleTaskScheduler taskScheduler,
    ProcessRepository processRepository,
    ProcessGroupRepository groupRepository) {
    this.taskScheduler = taskScheduler;
    this.processRepository = processRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if(isWindows)
      command = "cmd.exe /c dir";
    else
      command = "sh -c ls";

    processes();
    chains();
  }

  private void processes(){
    Process test1 = new Process();
    test1.setName("Test1");
    test1.setDirectory("/");
    test1.setCron("10 * * * * ?");
    test1.setCommand(command);
    test1.setStandalone(true);
    test1.setPriority(EProcessPriority.NORMAL);

    test1 = processRepository.save(test1);
    taskScheduler.schedule(test1);

    Process test2 = new Process();
    test2.setName("Test2");
    test2.setDirectory("/");
    test2.setCron("15 * * * * ?");
    test2.setCommand(command);
    test2.setPriority(EProcessPriority.NORMAL);
    test2.setStandalone(true);
    test2 = processRepository.save(test2);
    taskScheduler.schedule(test2);
  }

  private void chains(){

    Process test1 = new Process();
    test1.setName("Test3");
    test1.setDirectory("/");
    test1.setCommand(command);
    test1.setPriority(EProcessPriority.NORMAL);
    test1.setStandalone(false);
    test1 = processRepository.save(test1);

    Process test2 = new Process();
    test2.setName("Test4");
    test2.setDirectory("/");
    test2.setCommand(command);
    test2.setPriority(EProcessPriority.CRITICAL);
    test2.setStandalone(false);
    test2 = processRepository.save(test2);

    ProcessGroup processGroup = new ProcessGroup();
    processGroup.setCron("19 * * * * ?");
    processGroup.setName("Test Chain1");
    processGroup.addProcess(test1, 0);
    processGroup.addProcess(test2, 1);
    processGroup.setPriority(EProcessPriority.CRITICAL);
    processGroup = groupRepository.save(processGroup);
    taskScheduler.schedule(processGroup);
  }

}
