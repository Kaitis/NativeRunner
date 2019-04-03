package com.ak.native_runner_admin.notification;

import java.time.Instant;

public class ProcessResult extends AbstractProcessResult implements ProcessResultMessage {

  private String result;

  public ProcessResult of(String instanceName, String processName, Instant timestamp, String result){
    this.instanceName = instanceName;
    this.processName = processName;
    this.timestamp = timestamp;
    this.result = result;
    return this;
  }

  public String body(){
    return String.format("Process: %s completed on instance: %s on: %s " +
      "\n with Result: %s ", processName, instanceName, formatter.format(timestamp), result);
  }

  public String title() {
    return
      String.format("Process: %s Result: %s Instance: %s Time: %s ", processName, result, instanceName, formatter.format(timestamp));
  }
}