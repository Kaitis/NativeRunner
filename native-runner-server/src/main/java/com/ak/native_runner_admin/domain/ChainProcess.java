package com.ak.native_runner_admin.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "ChainProcess")
@Table(name = "chain_process")
public class ChainProcess implements Comparable<ChainProcess> {

  @EmbeddedId
  private ChainProcessId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("chainId")
  private ProcessGroup chain;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("processId")
  private Process process;

  @Column(name = "step")
  private int step;

  public ChainProcess() {
  }

  public ChainProcess(ProcessGroup chain, Process process, int step){
    this.chain = chain;
    this.process = process;
    this.step = step;
    this.id = new ChainProcessId(chain.getId(), process.getId());
  }

  @Override
  public int compareTo(ChainProcess o) {
    if(this.step == o.step)
      return 0;
    else
      return this.step > o.step ? 1 : -1;
  }

  public ChainProcessId getId() {
    return id;
  }

  public void setId(ChainProcessId id) {
    this.id = id;
  }

  public ProcessGroup getChain() {
    return chain;
  }

  public void setChain(ProcessGroup chain) {
    this.chain = chain;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }
}
