package com.ak.native_runner_admin.scheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;
import com.ak.native_runner_admin.domain.AbstractProcess;
import com.ak.native_runner_admin.domain.AbstractProcessHistory;
import com.ak.native_runner_admin.domain.EProcessPriority;
import com.ak.native_runner_admin.domain.EProcessStatus;
import com.ak.native_runner_admin.domain.Process;
import com.ak.native_runner_admin.domain.ProcessGroup;
import com.ak.native_runner_admin.domain.ProcessGroupHistory;
import com.ak.native_runner_admin.domain.ProcessHistory;
import com.ak.native_runner_admin.notification.ProcessError;
import com.ak.native_runner_admin.notification.ProcessResult;
import com.ak.native_runner_admin.notification.ProcessResultNotifier;
import com.ak.native_runner_admin.repository.ProcessGroupHistoryRepository;
import com.ak.native_runner_admin.repository.ProcessGroupRepository;
import com.ak.native_runner_admin.repository.ProcessHistoryRepository;
import com.ak.native_runner_admin.repository.ProcessRepository;
import com.ak.native_runner_admin.web.to.AbstractProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessGroupHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessGroupTO;
import com.ak.native_runner_admin.web.to.ProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessTO;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

@Component
@Scope("prototype")
public class RemoteProcessLauncher implements Runnable{

  static final Logger LOGGER = LoggerFactory.getLogger(RemoteProcessLauncher.class);

  private static final String  START_PROCESS_ENDPOINT        = "start-process";
  private static final String  STOP_PROCESS_ENDPOINT         = "stop-process";
  private static final String  START_PROCESS_GROUP_ENDPOINT  = "start-process-group";
  private static final String  STOP_PROCESS_GROUP_ENDPOINT   = "stop-process-group";
  private static final String  CLIENT_AVAILABLE_MEMORY       = "memory";
  private static final Integer MAX_RETRIES = 3;

  private AbstractProcess process;
  private AbstractProcessHistory processHistory;
  private String url;
  private String serverName = "";
  private int retryCount = 0;

  private final ThreadPoolTaskScheduler taskScheduler;
  private final ProcessRepository processRepository;
  private final ProcessGroupRepository groupRepository;
  private final InstanceRegistry instanceRegistry;
  private final ProcessHistoryRepository historyRepository;
  private final ProcessGroupHistoryRepository groupHistoryRepository;
  private final ProcessResultNotifier resultNotifier;

  public RemoteProcessLauncher(TaskScheduler taskScheduler,
    ProcessRepository processRepository,
    ProcessGroupRepository groupRepository, InstanceRegistry instanceRegistry,
    ProcessHistoryRepository historyRepository,
    ProcessGroupHistoryRepository groupHistoryRepository,
    ProcessResultNotifier errorNotifier) {
    this.taskScheduler = (ThreadPoolTaskScheduler) taskScheduler;
    this.processRepository = processRepository;
    this.groupRepository = groupRepository;
    this.instanceRegistry = instanceRegistry;
    this.historyRepository = historyRepository;
    this.groupHistoryRepository = groupHistoryRepository;
    this.resultNotifier = errorNotifier;
  }

  public RemoteProcessLauncher withProcess(AbstractProcess process){
    this.process = process;
    return this;
  }


  @Override
  public void run()  {
    Assert.notNull(process, "Process can not be null");

    url = process.getFixedURL() == null ? getAvailableNodeUrl() : process.getFixedURL();

    LOGGER.info("Launching process {}", process.getName());

    if(url == null) {
      LOGGER.warn("No instances found");
      return;
    }

    if(process instanceof Process) {
      LOGGER.info("Starting process  '{}' ", process.getName());
      processHistory = new ProcessHistory();

    }else if(process instanceof ProcessGroup) {
      LOGGER.debug("Starting process chain '{}' ", process.getName());
      processHistory = new ProcessGroupHistory();
      processHistory.setExecution_id(UUID.randomUUID().toString());
    }

    if(process.getStatus() == EProcessStatus.RUNNING){
      LOGGER.info("Process {} is already running!", process.getName());
      return;
    }


    process.setStatus(EProcessStatus.RUNNING);
    process.setLastExecutionUrl(url);
    processHistory.setResult(EProcessStatus.RUNNING);
    processHistory.setExecution_url(url);
    processHistory.setStartTime(LocalDateTime.now());

    persistProcess(null);

    taskScheduler.submitListenable(new RemoteProcessCallable())
      .addCallback(new ListenableFutureCallback<ResponseEntity<?>>() {
        @Override
        public void onFailure(Throwable throwable) {
          LOGGER.error("RemoteProcessCallable failed for process:" + process.getName(), throwable);
          handleProcessFailure(throwable);
        }

        @Override
        public void onSuccess(@Nullable ResponseEntity<?> entity) {
          if(entity != null) {
            LOGGER.debug("RemoteProcessCallable returned with entity body: {}", entity.getBody());
            if (entity.getStatusCode() == HttpStatus.OK)
              if (process instanceof Process)
                handleProcessSuccess((ProcessHistoryTO) entity.getBody());
              else
                handleProcessGroupSuccess((ProcessGroupHistoryTO) entity.getBody());

            else
              handleProcessFailure((Exception) entity.getBody());
          }
        }
      });
  }

  @Transactional
  public void handleProcessFailure(Throwable throwable) {
    process.setStatus(EProcessStatus.FAILED);
    processHistory.setStopTime(LocalDateTime.now());
    processHistory.setResult(EProcessStatus.FAILED);
    persistProcess(null);
    resultNotifier.notifyError(new ProcessError().of(serverName, process.getName(), Instant.now(),throwable));
    // retry if process is critical
    if (process.getPriority() == EProcessPriority.CRITICAL){
      LOGGER.info("Process {} is critical! Retrying...", process.getName());
      if (retryCount < MAX_RETRIES) {
        retryCount++;
        run();
      }else
        LOGGER.error("Process {} max retries reached!", process.getName());
    }
  }

  private void handleProcessSuccess(ProcessHistoryTO historyTO){
    LOGGER.info("Completed process '{}' with result {}", historyTO.getProcess(), historyTO.getResult());
    process.setStatus(historyTO.getResult());
    process.setLastExecutionDate(LocalDateTime.now());
    processHistory.setStopTime(historyTO.getStopTime() == null ? LocalDateTime.now() : historyTO.getStopTime());
    processHistory.setExitCode(historyTO.getExitCode());
    processHistory.setResult(historyTO.getResult());
    persistProcess(null);
    resultNotifier.notify(new ProcessResult().of(serverName, process.getName(), Instant.now(),historyTO.getResult().name()));
  }

  private void handleProcessGroupSuccess(ProcessGroupHistoryTO groupHistoryTO){
    LOGGER.info("Completed process chain '{}' with result {}", groupHistoryTO.getProcessGroup(), groupHistoryTO.getResult());
    process.setStatus(groupHistoryTO.getResult());
    process.setLastExecutionDate(LocalDateTime.now());
    processHistory.setStopTime(LocalDateTime.now());
    processHistory.setResult(groupHistoryTO.getResult());
    persistProcess(groupHistoryTO);
    resultNotifier.notify(new ProcessResult().of(serverName, process.getName(), Instant.now(),groupHistoryTO.getResult().name()));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void persistProcess(@Nullable ProcessGroupHistoryTO groupHistoryTO){

    try {
      if (process instanceof Process) {
        LOGGER.debug("Persisting process '{}'", process.getName());
        ((ProcessHistory) processHistory).setProcess((Process) process);
        processHistory = historyRepository.save((ProcessHistory) processHistory);
        ((Process) process).getProcessHistory().add((ProcessHistory) processHistory);
        process = processRepository.save((Process) process);
        LOGGER.debug("Done persisting!", process.getName());
      } else { // it is a chain
        LOGGER.debug("Persisting process group '{}'", process.getName());
        if (groupHistoryTO != null && groupHistoryTO.getProcessHistory() != null) {
          for (ProcessHistoryTO processHistoryTO : groupHistoryTO.getProcessHistory()) {
            ProcessHistory history = new ProcessHistory();
            Process p = processRepository.findById(processHistoryTO.getProcessID()).get();
            history.setStartTime(processHistoryTO.getStartTime());
            history.setStopTime(processHistoryTO.getStopTime());
            history.setExitCode(processHistoryTO.getExitCode());
            history.setResult(processHistoryTO.getResult());
            history.setExecution_id(processHistory.getExecution_id());
            history.setExecution_url(processHistory.getExecution_url());
            history.setProcess(p);
            p.getProcessHistory().add(history);
            historyRepository.save(history);
            processRepository.save(p);
          }
        }
        ((ProcessGroupHistory) processHistory).setProcessGroup((ProcessGroup) process);
        processHistory = groupHistoryRepository.save((ProcessGroupHistory) processHistory);
        ((ProcessGroup) process).getProcessGroupHistory().add((ProcessGroupHistory) processHistory);
        process = groupRepository.save((ProcessGroup) process);
        LOGGER.debug("Done persisting! {}", process.getName());
      }
    }catch (Exception e){
      LOGGER.error("Error while trying to persist process {}", process.getName(),e);
    }
  }

  @SuppressWarnings("unchecked")
  private String getAvailableNodeUrl() {

    List<Instance> instances =  instanceRegistry.getInstances().collectList().block();
    if(instances == null) return null;

    double nodeRating = 0.0;
    String serviceUrl = "";

    for (Instance instance : instances) {
      RestTemplate restTemplate = new RestTemplate();
      try {
        Map<String, Integer> memory =
          restTemplate.getForObject(instance.getRegistration().getServiceUrl() + CLIENT_AVAILABLE_MEMORY, Map.class);
        LOGGER.debug("Instance: {} Memory {} ", instance.getRegistration().getName(), memory);

        double instanceRating =  ((double) memory.get("available") / memory.get("max")) * 100;

        LOGGER.info("Instance: {} Rating {} ", instance.getRegistration().getName(), instanceRating);

        if (instanceRating > nodeRating) {
          nodeRating = instanceRating;
          serviceUrl = instance.getRegistration().getServiceUrl();
          serverName = instance.getRegistration().getName();
        }
      }catch (Exception e){
        LOGGER.error("Failed to get memory from instance: {} with error {}", instance.getRegistration().getName(), e.getLocalizedMessage());
        e.printStackTrace();
      }
    }
    if(StringUtils.isEmpty(serviceUrl)) {
      LOGGER.info("Using server {} with url: {}" ,serverName, serviceUrl);
    }

    return serviceUrl;
  }

  public void stop() {
    process.setStatus(EProcessStatus.STOPPED);
    processHistory.setStopTime(LocalDateTime.now());
    processHistory.setResult(EProcessStatus.STOPPED);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<ProcessHistoryTO> entity;

    if(process instanceof Process) {
      entity = restTemplate.postForEntity(url + STOP_PROCESS_ENDPOINT, new HttpEntity<>(new ProcessTO((Process) process)),
        ProcessHistoryTO.class);
      if (entity.getStatusCode() == HttpStatus.OK){
        LOGGER.info("Remote process  {} stopped", process);
        processRepository.save((Process)process);
      }

    }else {

      entity = restTemplate.postForEntity(url + STOP_PROCESS_GROUP_ENDPOINT, new HttpEntity<>(new ProcessGroupTO((ProcessGroup) process)),
        ProcessHistoryTO.class);
      if (entity.getStatusCode() == HttpStatus.OK){
        LOGGER.info("Remote process group {} stopped", process);
        groupRepository.save((ProcessGroup)process);
      }
    }
  }

  private class RemoteProcessCallable implements Callable<ResponseEntity<?>> {

    @Override
    public ResponseEntity<?> call() throws Exception {
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<? extends AbstractProcessHistoryTO> entity;

      if(process instanceof Process) {
        entity =
          restTemplate.postForEntity(url + START_PROCESS_ENDPOINT, new HttpEntity<>(new ProcessTO((Process) process)),
            ProcessHistoryTO.class);

      }else if(process instanceof ProcessGroup) {
        entity = restTemplate.postForEntity(url + START_PROCESS_GROUP_ENDPOINT,
          new HttpEntity<>(new ProcessGroupTO((ProcessGroup) process)), ProcessGroupHistoryTO.class);

      }else
        throw new Exception("process must be an instance of Process or ProcessGroup");

      return entity;
    }
  }

  public AbstractProcess getProcess() {
    return process;
  }

  public void setProcess(AbstractProcess process) {
    this.process = process;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
