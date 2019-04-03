package com.ak.native_runner_admin.web;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ak.native_runner_admin.service.ProcessService;
import com.ak.native_runner_admin.web.to.ProcessDetailTO;
import com.ak.native_runner_admin.web.to.ProcessTO;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class ProcessRestController {

  private ProcessService service;

  public ProcessRestController(ProcessService service) {
    this.service = service;
  }

  @PostMapping("/process")
  public void register(@RequestBody ProcessTO process) {
    service.register(process);
  }

  @PatchMapping("/process")
  public void update(@RequestBody ProcessTO process) {
    service.update(process);
  }

  @GetMapping("/process/{id}")
  public ProcessDetailTO processDetail(@PathVariable("id")Long id){
    return service.getProcessDetail(id);
  }

  @GetMapping("/processes")
  public List<ProcessTO> getProcesses(
    @RequestParam(value = "page", required = false) Integer page,
    @RequestParam(value = "size", required = false) Integer size){

    if(page == null || size == null)
      return service.findAllProcesses();
    else
      return service.findAPagedProcesses(PageRequest.of(page, size));
  }

  @PostMapping("/process/{id}/start")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void start(@PathVariable("id")Long id){
    service.start(id);
  }

  @PostMapping("/process/{id}/stop")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void stop(@PathVariable("id")Long id){
    service.stop(id);
  }

  @PostMapping("/process/unschedule/{process}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void unschedule(@PathVariable("process") String process){
    service.unschedule(process);
  }

  @DeleteMapping("/process/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void delete(@PathVariable("id")Long id){
    service.delete(id);
  }


}
