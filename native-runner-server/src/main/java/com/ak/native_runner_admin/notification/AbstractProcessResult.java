package com.ak.native_runner_admin.notification;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractProcessResult {

  protected String instanceName;
  protected Instant timestamp;
  protected String processName;
  protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm::ss" ).withZone(ZoneId.systemDefault());


}
