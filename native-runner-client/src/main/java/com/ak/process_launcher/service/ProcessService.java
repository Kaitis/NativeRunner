package com.ak.process_launcher.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import com.ak.process_launcher.web.to.ProcessGroupHistoryTO;
import com.ak.process_launcher.web.to.ProcessGroupTO;
import com.ak.process_launcher.web.to.ProcessHistoryTO;
import com.ak.process_launcher.web.to.ProcessTO;

public interface ProcessService {

  ProcessHistoryTO start(ProcessTO request)throws ExecutionException, InterruptedException;

  ProcessHistoryTO stop(ProcessTO request) throws Exception;

  ProcessGroupHistoryTO startGroup(ProcessGroupTO group) throws ExecutionException, InterruptedException;

  ProcessGroupHistoryTO stopGroup(ProcessGroupTO group) throws Exception;


}
