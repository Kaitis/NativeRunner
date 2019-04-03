package com.ak.process_launcher.web;

import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ak.process_launcher.service.ProcessService;
import com.ak.process_launcher.web.to.ProcessGroupTO;
import com.ak.process_launcher.web.to.ProcessTO;

@CrossOrigin
@RestController
public class LaunchingRestController {

  static final Logger LOGGER = LoggerFactory.getLogger(LaunchingRestController.class);

  private ProcessService service;

  public LaunchingRestController(ProcessService service) {
    this.service = service;
  }

  @PostMapping("/start-process")
  public ResponseEntity<?> start(RequestEntity<ProcessTO> process){
    LOGGER.info("Received start command for process {}", process.getBody().getName());
    try {
      return ResponseEntity.ok(service.start(process.getBody()));
    }
    catch (ExecutionException | InterruptedException e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
    }
  }

  @PostMapping("/stop-process")
  public ResponseEntity<?> stop(RequestEntity<ProcessTO> process){
      LOGGER.info("Received stop command for process {}", process.getBody().getName());
    try {
      return ResponseEntity.ok(service.stop(process.getBody()));
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
    }
  }

  @PostMapping("/start-process-group")
  public ResponseEntity<?> startGroup(RequestEntity<ProcessGroupTO> processGroup){
    LOGGER.info("Received start command for process {}", processGroup.getBody().getName());
    try {
      return ResponseEntity.ok(service.startGroup(processGroup.getBody()));
    }
    catch (ExecutionException | InterruptedException e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
    }
  }

  @PostMapping("/stop-process-group")
  public ResponseEntity<?> stopGroup(RequestEntity<ProcessGroupTO> processGroup){
    LOGGER.info("Received stop command for process {}", processGroup.getBody().getName());
    try {
      return ResponseEntity.ok(service.stopGroup(processGroup.getBody()));
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
    }
  }

}
