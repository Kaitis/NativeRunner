package com.ak.native_runner_admin.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ak.native_runner_admin.service.ProcessService;
import com.ak.native_runner_admin.web.to.AbstractProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ScheduledTaskTO;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import reactor.core.publisher.Flux;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class ProcessAdminRestController {

  private final InstanceRegistry instanceRegistry;
  private final ProcessService service;

  @Autowired
  public ProcessAdminRestController(InstanceRegistry instanceRegistry,
    ProcessService service) {
    this.instanceRegistry = instanceRegistry;
    this.service = service;
  }

  @GetMapping("/live-instances")
  public Flux<Instance> getInstances(){
    return instanceRegistry.getInstances();
  }

  @GetMapping("/schedule")
  public Collection<ScheduledTaskTO> getSchedule(){
    return service.getSchedule();
  }

  @GetMapping("/history")
  public Collection<AbstractProcessHistoryTO> getHistory(
    @RequestParam(value = "page", required = false) Integer page,
    @RequestParam(value = "size", required = false) Integer size){

    if(page == null || size == null)
      return service.getAllHistory();
    else
      return service.getPagedHistory(PageRequest.of(page, size, Sort.Direction.DESC,"startTime"));
  }
}
