package com.ak.native_runner_admin.domain;

import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractProcessHistory extends AbtractDomainObject {

  private LocalDateTime startTime;
  private LocalDateTime stopTime;
  @Enumerated(EnumType.STRING)
  private EProcessStatus result;
  private int exitCode;
  private String execution_id;
  private String execution_url;

  public AbstractProcessHistory() {
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

  public int getExitCode() {
    return exitCode;
  }

  public void setExitCode(int exitCode) {
    this.exitCode = exitCode;
  }

  public String getExecution_id() {
    return execution_id;
  }

  public void setExecution_id(String execution_id) {
    this.execution_id = execution_id;
  }

  public String getExecution_url() {
    return execution_url;
  }

  public void setExecution_url(String execution_url) {
    this.execution_url = execution_url;
  }
}
