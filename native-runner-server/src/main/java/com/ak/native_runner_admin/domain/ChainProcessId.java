package com.ak.native_runner_admin.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChainProcessId implements Serializable {

  @Column(name = "chain_id")
  private Long chainId;

  @Column(name = "process_id")
  private Long processId;

  private ChainProcessId() {}

  public ChainProcessId(
    Long chainId,
    Long processId) {
    this.chainId = chainId;
    this.processId = processId;
  }

  public Long getChainId() {
    return chainId;
  }

  public void setChainId(Long chainId) {
    this.chainId = chainId;
  }

  public Long getProcessId() {
    return processId;
  }

  public void setProcessId(Long processId) {
    this.processId = processId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ChainProcessId that = (ChainProcessId) o;
    return Objects.equals(chainId, that.chainId) &&
      Objects.equals(processId, that.processId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chainId, processId);
  }
}
