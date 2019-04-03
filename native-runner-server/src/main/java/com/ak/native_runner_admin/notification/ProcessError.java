package com.ak.native_runner_admin.notification;

import java.time.Instant;

public class ProcessError extends AbstractProcessResult implements ProcessResultMessage {

  private Throwable throwable;

  public ProcessError of(String instanceName, String processName, Instant timestamp, Throwable throwable){
    this.instanceName = instanceName;
    this.processName = processName;
    this.timestamp = timestamp;
    this.throwable = throwable;
    return this;
  }

  public String body(){
    return String.format("Process: %s FAILED on instance: %s on: %s " +
      "\n with Cause: %s " +
      "\n and Message: %s", processName, instanceName, formatter.format(timestamp), throwable.getCause(), throwable.getMessage());
  }

  public String title() {
    return
      String.format("Process: %s FAILED on instance: %s on: %s ", processName, instanceName, formatter.format(timestamp));
  }
}