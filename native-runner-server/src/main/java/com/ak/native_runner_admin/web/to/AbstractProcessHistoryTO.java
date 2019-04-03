package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ak.native_runner_admin.domain.AbstractProcessHistory;
import com.ak.native_runner_admin.domain.EProcessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractProcessHistoryTO extends AbstractTO implements Serializable {

  static final long serialVersionUID = 134L;
  LocalDateTime startTime;
  LocalDateTime stopTime;
  EProcessStatus result;

  public AbstractProcessHistoryTO() {
  }

  public AbstractProcessHistoryTO(AbstractProcessHistory processHistory) {
    this.startTime = processHistory.getStartTime();
    this.stopTime = processHistory.getStopTime();
    this.result = processHistory.getResult();
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
