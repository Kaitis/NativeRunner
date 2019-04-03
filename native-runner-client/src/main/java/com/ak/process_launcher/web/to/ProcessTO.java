package com.ak.process_launcher.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProcessTO extends AbstractTO implements Serializable {

  static final long serialVersionUID = 12L;

  private Long id;
  private String name;
  private String directory;
  private String command;
  private EProcessStatus status;
  private String cron;
  private LocalDateTime lastExecutionDate;

  public ProcessTO() {
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
}
