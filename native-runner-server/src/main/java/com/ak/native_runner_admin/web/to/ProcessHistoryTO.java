package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import com.ak.native_runner_admin.domain.ProcessHistory;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessHistoryTO  extends AbstractProcessHistoryTO implements Serializable {

  static final long serialVersionUID = 134L;

  private Long processID;
  private String process;
  private String executionUrl;
  private int exitCode;

  public ProcessHistoryTO() {
  }

  public ProcessHistoryTO(ProcessHistory processHistory) {
    super(processHistory);
    this.processID = processHistory.getProcess().getId();
    this.process = processHistory.getProcess().getName();
    this.executionUrl = processHistory.getExecution_url();
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

  public int getExitCode() {
    return exitCode;
  }

  public void setExitCode(int exitCode) {
    this.exitCode = exitCode;
  }

  public String getExecutionUrl() {
    return executionUrl;
  }

  public void setExecutionUrl(String executionUrl) {
    this.executionUrl = executionUrl;
  }
}
