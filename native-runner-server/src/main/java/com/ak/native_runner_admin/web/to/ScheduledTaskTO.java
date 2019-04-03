package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ScheduledTaskTO extends AbstractTO implements Serializable{

  static final long serialVersionUID = 1L;

  private String process;
  private LocalDateTime nextRunTimestamp;

  public ScheduledTaskTO(String process, LocalDateTime nextRunTimestamp) {
    this.process = process;
    this.nextRunTimestamp = nextRunTimestamp;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public LocalDateTime getNextRunTimestamp() {
    return nextRunTimestamp;
  }

  public void setNextRunTimestamp(LocalDateTime nextRunTimestamp) {
    this.nextRunTimestamp = nextRunTimestamp;
  }


}
