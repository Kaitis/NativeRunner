package com.ak.native_runner_admin.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;

@Entity
public class ProcessGroup extends AbstractProcess {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_group_seq")
  @SequenceGenerator(name="process_group_seq", sequenceName = "PROCESS_GROUP_SEQ", allocationSize = 1)
  private Long id;

  @OneToMany(mappedBy = "processGroup")
  @Fetch(FetchMode.JOIN)
  private Set<ProcessGroupHistory> processGroupHistory = new HashSet<>();

  @OneToMany(mappedBy = "chain", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy(clause = "step asc")
  private SortedSet<ChainProcess> processChain = new TreeSet<>();

  public void addProcess(Process process, int step){
    ChainProcess chainProcess = new ChainProcess(this,process,step);
    process.setCron(null);
    process.setStandalone(false);
    process.getGroups().add(chainProcess);
    processChain.add(chainProcess);
  }

  public Set<ProcessGroupHistory> getProcessGroupHistory() {
    return processGroupHistory;
  }

  public void setProcessGroupHistory(
    Set<ProcessGroupHistory> processGroupHistory) {
    this.processGroupHistory = processGroupHistory;
  }

  public SortedSet<ChainProcess> getProcessChain() {
    return processChain;
  }

  public void setProcessChain(SortedSet<ChainProcess> processChain) {
    this.processChain = processChain;
  }


  public Long getId() {
    return id;
  }
}
