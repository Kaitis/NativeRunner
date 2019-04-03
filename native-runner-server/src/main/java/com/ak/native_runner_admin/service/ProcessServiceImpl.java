package com.ak.native_runner_admin.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.ak.native_runner_admin.domain.EProcessPriority;
import com.ak.native_runner_admin.domain.EProcessStatus;
import com.ak.native_runner_admin.domain.Process;
import com.ak.native_runner_admin.domain.ProcessGroup;
import com.ak.native_runner_admin.repository.ProcessGroupHistoryRepository;
import com.ak.native_runner_admin.repository.ProcessGroupRepository;
import com.ak.native_runner_admin.repository.ProcessHistoryRepository;
import com.ak.native_runner_admin.repository.ProcessRepository;
import com.ak.native_runner_admin.scheduler.RemoteProcessLauncher;
import com.ak.native_runner_admin.scheduler.SimpleTaskScheduler;
import com.ak.native_runner_admin.web.to.AbstractProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessDetailTO;
import com.ak.native_runner_admin.web.to.ProcessGroupDetailTO;
import com.ak.native_runner_admin.web.to.ProcessGroupHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessGroupTO;
import com.ak.native_runner_admin.web.to.ProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessTO;
import com.ak.native_runner_admin.web.to.ScheduledTaskTO;

@Service
public class ProcessServiceImpl implements ProcessService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);

  private ProcessRepository repository;
  private ProcessGroupRepository groupRepository;
  private ProcessHistoryRepository historyRepository;
  private ProcessGroupHistoryRepository groupHistoryRepository;
  private ApplicationContext context;
  private SimpleTaskScheduler taskScheduler;

  @Autowired
  public ProcessServiceImpl(ProcessRepository repository,
    ProcessGroupRepository groupRepository,
    ProcessHistoryRepository historyRepository,
    ProcessGroupHistoryRepository groupHistoryRepository,
    ApplicationContext context,
    SimpleTaskScheduler taskScheduler) {
    this.repository = repository;
    this.groupRepository = groupRepository;
    this.historyRepository = historyRepository;
    this.groupHistoryRepository = groupHistoryRepository;
    this.context = context;
    this.taskScheduler = taskScheduler;
  }

  @Override
  public ProcessTO register(ProcessTO request) {

    LOGGER.info("Registering process request: {}", request);

    Process process = new Process();
    process.setName(request.getName());
    process.setDirectory(request.getDirectory());
    process.setCron(request.getCron());

    process.setCommand(request.getCommand());
    process.setPriority(request.getPriority());
    process = repository.save(process);

    if (!StringUtils.isEmpty(process.getCron()))
      taskScheduler.schedule(process);

    request.setId(process.getId());
    return request;
  }

  @Override
  @Transactional
  public void update(ProcessTO process) {
    LOGGER.info("Updating process: {}", process);

    Process existingProcess = repository.findById(process.getId()).get();
    existingProcess.setStatus(process.getStatus());
    existingProcess.setCron(process.getCron());
    existingProcess.setCommand(process.getCommand());
    existingProcess.setDirectory(process.getDirectory());
    existingProcess.setName(process.getName());
    existingProcess.setPriority(process.getPriority());
    repository.save(existingProcess);
  }

  @Override
  public ProcessDetailTO getProcessDetail(Long id) throws IllegalArgumentException {
    return new ProcessDetailTO(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not valid")));
  }

  @Override
  public void start(Long id) {
    LOGGER.info("Trying to start process with id: {}", id);
    RemoteProcessLauncher processLauncher = context.getAutowireCapableBeanFactory().createBean(RemoteProcessLauncher.class);
    Optional<Process> process = repository.findById(id);
    if(process.isPresent() && process.get().getStatus() != EProcessStatus.RUNNING){
      processLauncher.withProcess(process.get());
      Executors.newSingleThreadExecutor().submit(processLauncher);
    }
  }

  @Override
  public void stop(Long id) {
    LOGGER.info("Trying to stop process with id: {}", id);
    Optional<Process> process = repository.findById(id);
    if(process.isPresent() && process.get().getStatus() == EProcessStatus.RUNNING){
      Collection<RemoteProcessLauncher> launchers =context.getBeansOfType(RemoteProcessLauncher.class).values();
      if (!launchers.isEmpty()){
        LOGGER.info("Context returned launchers: {}",  launchers);
        Optional<RemoteProcessLauncher> launcherOptional = launchers
                    .stream()
                    .filter(r -> Objects.equals(r.getProcess().getId(), id))
                    .findFirst();

        if (launcherOptional.isPresent()){
          LOGGER.info("Found process launcher. Stopping....");
          launcherOptional.get().stop();
        }
      }
    }
  }

  @Override
  public List<ProcessTO> findAllProcesses() {
    List<ProcessTO> result = new ArrayList<>();
    repository.findAll().forEach(p -> result.add(new ProcessTO(p)));
    return result;
  }

  @Override
  public List<ProcessTO> findAPagedProcesses(Pageable pageRequest) {
    List<ProcessTO> result = new ArrayList<>();
    repository.findAll(pageRequest).forEach(p -> result.add(new ProcessTO(p)));
    return result;
  }

  @Override
  public List<AbstractProcessHistoryTO> getAllHistory() {
    List<AbstractProcessHistoryTO> result = new ArrayList<>();
    historyRepository.findStandalone().forEach(h -> result.add(new ProcessHistoryTO(h)));
    groupHistoryRepository.findAll().forEach(g -> result.add(new ProcessGroupHistoryTO(g)));
    result.sort(Comparator.comparing(AbstractProcessHistoryTO::getStartTime));
    return result;
  }

  @Override
  public List<AbstractProcessHistoryTO> getPagedHistory(Pageable pageRequest) {
    List<AbstractProcessHistoryTO> result = new ArrayList<>();
    historyRepository.findStandalone(pageRequest).forEach(h -> result.add(new ProcessHistoryTO(h)));
    groupHistoryRepository.findAll(pageRequest).forEach(g -> result.add(new ProcessGroupHistoryTO(g)));
    result.sort(Comparator.comparing(AbstractProcessHistoryTO::getStartTime));
    return result;
  }

  @Override
  public List<ProcessGroupTO> getProcessGroups() {
    List<ProcessGroupTO> result = new ArrayList<>();
    groupRepository.findAll().forEach(g -> result.add(new ProcessGroupTO(g)));
    return result;
  }

  @Override
  @Transactional
  public ProcessGroupTO register(ProcessGroupTO group) {
    LOGGER.info("Registering process group: {}", group);
    ProcessGroup processGroup;

    if (null == group.getId())
      processGroup = new ProcessGroup();
    else
      processGroup = groupRepository.findById(group.getId()).get();

    processGroup.setName(group.getName());
    processGroup.setCron(group.getCron());
    processGroup.setPriority(group.getPriority() == 0 ? EProcessPriority.NORMAL : EProcessPriority.CRITICAL );

    group.getProcesses()
      .forEach(p -> processGroup.addProcess(repository.findById(p.getProcessTO().getId()).get(), p.getStep()));


    taskScheduler.schedule(groupRepository.save(processGroup));
    group.setId(processGroup.getId());
    return group;
  }

  @Override
  public ProcessGroupTO getProcessGroupDetail(Long id) {
    return new ProcessGroupTO(groupRepository.findById(id).get());
  }

  @Override
  public ProcessGroupDetailTO getProcessGroupHistoryDetail(Long id) {
    return new ProcessGroupDetailTO(groupRepository.findById(id).get());
  }

  @Override
  public void startGroup(Long id) {
    LOGGER.info("Trying to start process group with id: {}", id);
    RemoteProcessLauncher processGroupRunner = context.getAutowireCapableBeanFactory().createBean(RemoteProcessLauncher.class);
    Optional<ProcessGroup> processGroup = groupRepository.findById(id);
    if(processGroup.isPresent()){
      processGroupRunner.withProcess(processGroup.get());
      Executors.newSingleThreadExecutor().submit(processGroupRunner);
    }
  }

  @Override
  public void stopGroup(Long id) {
    LOGGER.info("Trying to stop process group with id: {}", id);
    Optional<ProcessGroup> processGroup = groupRepository.findById(id);
    if(processGroup.isPresent() && processGroup.get().getStatus() == EProcessStatus.RUNNING){
      context.getBeansOfType(RemoteProcessLauncher.class).values()
        .stream()
        .filter(r -> r.getProcess().getName().equalsIgnoreCase(processGroup.get().getName()))
        .findFirst()
        .ifPresent(RemoteProcessLauncher::stop);
    }
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Optional<Process> process = repository.findById(id);
    if (process.isPresent()) {
      if (process.get().getStatus() == EProcessStatus.RUNNING)
        stop(id);
      repository.delete(process.get());
      taskScheduler.remove(process.get());
    }
  }

  @Override
  @Transactional
  public void deleteGroup(Long id) {
    Optional<ProcessGroup> processGroup = groupRepository.findById(id);
    if (processGroup.isPresent()) {
      if (processGroup.get().getStatus() == EProcessStatus.RUNNING)
        stopGroup(id);
      groupRepository.delete(processGroup.get());
      taskScheduler.remove(processGroup.get());
    }
  }

  @Override
  public List<ScheduledTaskTO> getSchedule() {
    List<ScheduledTaskTO> schedule = new ArrayList<>();
    taskScheduler.getActiveScheduleMap().forEach((s, scheduledFuture) ->
      schedule.add(new ScheduledTaskTO(s, LocalDateTime.now()
        .plus(scheduledFuture.getDelay(TimeUnit.MILLISECONDS), ChronoUnit.MILLIS))));

    return schedule;
  }

  @Override
  public void unschedule(String process) {
    Optional<Process> optionalProcess = repository.findByName(process);
    LOGGER.debug("Trying to unschedule process: {}", process);
    if (optionalProcess.isPresent()) {
      LOGGER.debug("Process found! Removing...");
      taskScheduler.remove(optionalProcess.get());
    }else {
      LOGGER.debug("Process not found! Looking at chains...");
      Optional<ProcessGroup> optionalChain = groupRepository.findByName(process);
      optionalChain.ifPresent(r -> taskScheduler.remove(r));
    }
  }
}
