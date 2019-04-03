package com.ak.native_runner_admin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ProcessHistory extends AbstractProcessHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_hist_seq")
  @SequenceGenerator(name="process_hist_seq", sequenceName = "process_hist_seq", allocationSize = 1)
  private Long id;

  @ManyToOne
  private Process process;

  public ProcessHistory() {
  }

  public Long getId() {
    return id;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }


}
