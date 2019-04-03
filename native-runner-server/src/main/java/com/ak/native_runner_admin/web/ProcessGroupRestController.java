package com.ak.native_runner_admin.web;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ak.native_runner_admin.service.ProcessService;
import com.ak.native_runner_admin.web.to.ProcessGroupDetailTO;
import com.ak.native_runner_admin.web.to.ProcessGroupTO;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class ProcessGroupRestController {

  private ProcessService service;

  public ProcessGroupRestController(ProcessService service) {
    this.service = service;
  }

  @PostMapping("/chain/register")
  public void registerGroup(@RequestBody ProcessGroupTO group) {
    service.register(group);
  }

  @GetMapping("/chains")
  public List<ProcessGroupTO> getChains(){
    return service.getProcessGroups();
  }

  @GetMapping("/chain/{id}")
  public ProcessGroupTO processGroupDetail(@PathVariable("id")Long id){
    return service.getProcessGroupDetail(id);
  }

  @GetMapping("/chain/{id}/detail")
  public ProcessGroupDetailTO processGroupHistoryDetail(@PathVariable("id")Long id){
    return service.getProcessGroupHistoryDetail(id);
  }

  @PostMapping("/chain/{id}/start")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void startGroup(@PathVariable("id")Long id){
    service.startGroup(id);
  }

  @PostMapping("/chain/{id}/stop")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void stopGroup(@PathVariable("id")Long id){
    service.stopGroup(id);
  }

  @DeleteMapping("/chain/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteGroup(@PathVariable("id")Long id){
    service.deleteGroup(id);
  }
}
