package com.ak.native_runner_admin.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.ak.native_runner_admin.web.to.AbstractProcessHistoryTO;
import com.ak.native_runner_admin.web.to.ProcessDetailTO;
import com.ak.native_runner_admin.web.to.ProcessGroupDetailTO;
import com.ak.native_runner_admin.web.to.ProcessGroupTO;
import com.ak.native_runner_admin.web.to.ProcessTO;
import com.ak.native_runner_admin.web.to.ScheduledTaskTO;

public interface ProcessService {

  void start(Long id);

  void stop(Long id);

  void delete(Long id);

  ProcessTO register(ProcessTO request);

  List<ProcessTO> findAllProcesses();

  List<AbstractProcessHistoryTO> getAllHistory();

  List<AbstractProcessHistoryTO> getPagedHistory(Pageable pageRequest);

  List<ProcessTO> findAPagedProcesses(Pageable pageRequest);

  ProcessDetailTO getProcessDetail(Long id);

  List<ProcessGroupTO> getProcessGroups();

  ProcessGroupTO register(ProcessGroupTO group);

  ProcessGroupTO getProcessGroupDetail(Long id);

  void startGroup(Long id);

  void stopGroup(Long id);

  void deleteGroup(Long id);

  void update(ProcessTO process);

  List<ScheduledTaskTO> getSchedule();

  ProcessGroupDetailTO getProcessGroupHistoryDetail(Long id);

  void unschedule(String process);
}
