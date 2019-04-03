package com.ak.native_runner_admin.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
public class Process extends AbstractProcess {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_id_seq")
  @SequenceGenerator(name="process_id_seq", sequenceName = "PROCESS_ID_SEQ", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String directory;

  @Column(nullable = false)
  private String command;

  private Boolean standalone = true;

  @OneToMany(mappedBy = "process", fetch = FetchType.EAGER)
  private Set<ProcessHistory> processHistory = new HashSet<>();

  @OneToMany(mappedBy = "process", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChainProcess> groups = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public Boolean isStandalone() {
    return standalone;
  }

  public void setStandalone(Boolean standalone) {
    this.standalone = standalone;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public Set<ProcessHistory> getProcessHistory() {
    return processHistory;
  }

  public void setProcessHistory(Set<ProcessHistory> processHistory) {
    this.processHistory = processHistory;
  }

  public List<ChainProcess> getGroups() {
    return groups;
  }

  public void setGroups(List<ChainProcess> groups) {
    this.groups = groups;
  }

}
