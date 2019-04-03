package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.ak.native_runner_admin.domain.EProcessStatus;
import com.ak.native_runner_admin.domain.ProcessGroup;


public class ProcessGroupTO extends AbstractTO implements Serializable{

  static final long serialVersionUID = 123L;

  private Long id;
  private String name;
  private List<ChainProcessTO> processes;
  private EProcessStatus status;
  private String cron;
  private Integer priority;
  private LocalDateTime lastExecutionDate;
  private String lastExecutionUrl;

  public ProcessGroupTO() {
  }

  public ProcessGroupTO(ProcessGroup group) {
    this.id = group.getId();
    this.name = group.getName();
    this.status = group.getStatus();
    this.cron = group.getCron();
    this.lastExecutionDate = group.getLastExecutionDate();
    this.lastExecutionUrl = group.getLastExecutionUrl() == null ? "" :  group.getLastExecutionUrl();
    this.processes = group.getProcessChain().stream()
      .map(cp -> new ChainProcessTO(new ProcessTO(cp.getProcess()), cp.getStep()))
      .collect(Collectors.toList());
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public LocalDateTime getLastExecutionDate() {
    return lastExecutionDate;
  }

  public void setLastExecutionDate(LocalDateTime lastExecutionDate) {
    this.lastExecutionDate = lastExecutionDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ChainProcessTO> getProcesses() {
    return processes;
  }

  public void setProcesses(List<ChainProcessTO> processes) {
    this.processes = processes;
  }

  public EProcessStatus getStatus() {
    return status;
  }

  public void setStatus(EProcessStatus status) {
    this.status = status;
  }

  public String getCron() {
    return cron;
  }

  public void setCron(String cron) {
    this.cron = cron;
  }

  public String getLastExecutionUrl() {
    return lastExecutionUrl;
  }

  public void setLastExecutionUrl(String lastExecutionUrl) {
    this.lastExecutionUrl = lastExecutionUrl;
  }
}
