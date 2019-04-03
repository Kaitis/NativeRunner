package com.ak.process_launcher.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ProcessGroupTO extends AbstractTO implements Serializable{

  static final long serialVersionUID = 123L;

  private Long id;
  private String name;
  private List<ChainProcessTO> processes;
  private EProcessStatus status;
  private String cron;
  private LocalDateTime lastExecutionDate;

  public ProcessGroupTO() {
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
}
