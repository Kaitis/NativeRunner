package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ak.native_runner_admin.domain.EProcessPriority;
import com.ak.native_runner_admin.domain.EProcessStatus;
import com.ak.native_runner_admin.domain.Process;

public class ProcessTO extends AbstractTO implements Serializable {

  static final long serialVersionUID = 12L;

  private Long id;
  private String name;
  private String directory;
  private String command;
  private EProcessStatus status;
  private String cron;
  private EProcessPriority priority;
  private LocalDateTime lastExecutionDate;
  private String lastExecutionUrl;

  public ProcessTO() {
  }

  public ProcessTO(Process process) {
    this.id = process.getId();
    this.name = process.getName();
    this.directory = process.getDirectory();
    this.command = process.getCommand();
    this.status = process.getStatus();
    this.cron = process.getCron();
    this.lastExecutionDate = process.getLastExecutionDate();
    this.lastExecutionUrl = process.getLastExecutionUrl() == null ? "" : process.getLastExecutionUrl();
  }

  public EProcessPriority getPriority() {
    return priority;
  }

  public void setPriority(EProcessPriority priority) {
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

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
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
