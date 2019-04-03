package com.ak.process_launcher.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessHistoryTO extends AbstractTO implements Serializable {

  static final long serialVersionUID = 134L;

  private Long processID;
  private String process;
  private LocalDateTime startTime;
  private LocalDateTime stopTime;
  private EProcessStatus result;

  public ProcessHistoryTO() {
  }

  public Long getProcessID() {
    return processID;
  }

  public void setProcessID(Long processID) {
    this.processID = processID;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getStopTime() {
    return stopTime;
  }

  public void setStopTime(LocalDateTime stopTime) {
    this.stopTime = stopTime;
  }

  public EProcessStatus getResult() {
    return result;
  }

  public void setResult(EProcessStatus result) {
    this.result = result;
  }
}
