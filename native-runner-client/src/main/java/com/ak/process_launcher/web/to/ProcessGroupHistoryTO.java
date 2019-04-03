package com.ak.process_launcher.web.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessGroupHistoryTO extends AbstractTO implements Serializable {

  static final long serialVersionUID = 134L;

  private Long processGroupId;
  private String processGroup;
  private List<ProcessHistoryTO> processHistory;
  private LocalDateTime startTime;
  private LocalDateTime stopTime;
  private EProcessStatus result;

  public ProcessGroupHistoryTO() {
  }

  public List<ProcessHistoryTO> getProcessHistory() {
    return processHistory;
  }

  public void setProcessHistory(List<ProcessHistoryTO> processHistory) {
    this.processHistory = processHistory;
  }

  public Long getProcessGroupId() {
    return processGroupId;
  }

  public void setProcessGroupId(Long processGroupId) {
    this.processGroupId = processGroupId;
  }

  public String getProcessGroup() {
    return processGroup;
  }

  public void setProcessGroup(String processGroup) {
    this.processGroup = processGroup;
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
