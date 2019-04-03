package com.ak.native_runner_admin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ProcessGroupHistory extends AbstractProcessHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_group_hist_seq")
  @SequenceGenerator(name="process_group_hist_seq", sequenceName = "process_group_hist_seq", allocationSize = 1)
  private Long id;

  @ManyToOne
  private ProcessGroup processGroup;

  public ProcessGroupHistory() {
  }

  public ProcessGroup getProcessGroup() {
    return processGroup;
  }

  public void setProcessGroup(ProcessGroup processGroup) {
    this.processGroup = processGroup;
  }

  public Long getId() {
    return id;
  }
}
