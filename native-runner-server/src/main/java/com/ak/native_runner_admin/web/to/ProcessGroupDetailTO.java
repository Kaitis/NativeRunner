package com.ak.native_runner_admin.web.to;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.ak.native_runner_admin.domain.ProcessGroup;

public class ProcessGroupDetailTO extends ProcessGroupTO  {

  static final long serialVersionUID = 123L;

  private List<ProcessGroupHistoryTO> history = new ArrayList<>();

  public ProcessGroupDetailTO(ProcessGroup process) {
    super(process);
    history.addAll(process.getProcessGroupHistory().stream()
      .map(ProcessGroupHistoryTO::new)
      .sorted(Comparator.comparing(AbstractProcessHistoryTO::getStartTime).reversed())
      .collect(Collectors.toList()));
  }

  public List<ProcessGroupHistoryTO> getHistory() {
    return history;
  }

  public void setHistory(List<ProcessGroupHistoryTO> history) {
    this.history = history;
  }
}