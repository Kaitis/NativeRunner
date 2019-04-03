package com.ak.native_runner_admin.web.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.ak.native_runner_admin.domain.ChainProcess;
import com.ak.native_runner_admin.domain.Process;
import com.ak.native_runner_admin.domain.ProcessGroupHistory;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessGroupHistoryTO extends AbstractProcessHistoryTO implements Serializable {

  static final long serialVersionUID = 134L;

  private Long processGroupId;
  private String processGroup;
  private String executionUrl;
  private List<ProcessHistoryTO> processHistory = new ArrayList<>();

  public ProcessGroupHistoryTO() {
  }

  public ProcessGroupHistoryTO(ProcessGroupHistory processGroupHistory) {
    super(processGroupHistory);
    this.processGroupId = processGroupHistory.getProcessGroup().getId();
    this.processGroup = processGroupHistory.getProcessGroup().getName();
    this.executionUrl = processGroupHistory.getExecution_url();
    processGroupHistory.getProcessGroup().getProcessChain().stream()
      .map(ChainProcess::getProcess)
      .map(Process::getProcessHistory)
      .forEach(list -> {
        if (list.size() > 0)
          list.stream()
            .filter(h -> h.getExecution_id().equalsIgnoreCase(processGroupHistory.getExecution_id()))
            .findFirst()
            .ifPresent(h -> processHistory.add(new ProcessHistoryTO(h)));
      });
  }

  public List<ProcessHistoryTO> getProcessHistory() {
    return processHistory;
  }

  public void setProcessHistory(List<ProcessHistoryTO> processHistory) {
    this.processHistory = processHistory;
  }

  public Long getProcessGroupId() {
    return processGroupId;
  }

  public void setProcessGroupId(Long processGroupId) {
    this.processGroupId = processGroupId;
  }

  public String getProcessGroup() {
    return processGroup;
  }

  public void setProcessGroup(String processGroup) {
    this.processGroup = processGroup;
  }

  public String getExecutionUrl() {
    return executionUrl;
  }

  public void setExecutionUrl(String executionUrl) {
    this.executionUrl = executionUrl;
  }
}
