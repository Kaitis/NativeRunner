package com.ak.process_launcher.web;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

@RestController
public class MonitoringRestController {

  static final Logger LOGGER = LoggerFactory.getLogger(MonitoringRestController.class);

  @GetMapping(value = "/memory")
  public Map<String,Long> getAvailableMemory(){
    LOGGER.debug("Memory request received");
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    Long av =  hal.getMemory().getAvailable();
    Long max =  hal.getMemory().getTotal();
    HashMap <String,Long> result = new HashMap<>();
    result.put("available", av);
    result.put("max", max);
    LOGGER.info("Memory Result: available: {}  max: {}", av,max);
    return result;
  }
}
