package com.ak.native_runner_admin.web.to;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.ak.native_runner_admin.domain.Process;

public class ProcessDetailTO extends ProcessTO  {

  static final long serialVersionUID = 123L;

  private List<ProcessHistoryTO> history = new ArrayList<>();

  public ProcessDetailTO() {
  }

  public ProcessDetailTO(Process process) {
    super(process);
    history.addAll(process.getProcessHistory().stream()
      .map(ProcessHistoryTO::new)
      .sorted(Comparator.comparing(AbstractProcessHistoryTO::getStartTime).reversed())
      .collect(Collectors.toList()));
  }

  public List<ProcessHistoryTO> getHistory() {
    return history;
  }

  public void setHistory(List<ProcessHistoryTO> history) {
    this.history = history;
  }
}