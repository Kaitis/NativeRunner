package com.ak.native_runner_admin.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractProcess extends AbtractDomainObject{

  @Column(unique = true, nullable = false)
  String name;

  @Enumerated(EnumType.STRING)
  EProcessStatus status;

  @Column(length = 20)
  String cron;

  LocalDateTime lastExecutionDate;

  @Column(length = 16)
  @Enumerated(EnumType.STRING)
  EProcessPriority priority = EProcessPriority.NORMAL;

  String fixedURL;

  String lastExecutionUrl;

  public EProcessPriority getPriority() {
    return priority;
  }

  public void setPriority(EProcessPriority priority) {
    this.priority = priority;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getLastExecutionDate() {
    return lastExecutionDate;
  }

  public void setLastExecutionDate(LocalDateTime lastExecutionDate) {
    this.lastExecutionDate = lastExecutionDate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EProcessStatus getStatus() {
    return status;
  }

  public void setStatus(EProcessStatus status) {
    this.status = status;
  }

  public String getCron() {
    return cron;
  }

  public void setCron(String cron) {
    this.cron = cron;
  }

  public String getFixedURL() {
    return fixedURL;
  }

  public void setFixedURL(String fixedURL) {
    this.fixedURL = fixedURL;
  }

  public String getLastExecutionUrl() {
    return lastExecutionUrl;
  }

  public void setLastExecutionUrl(String lastExecutionUrl) {
    this.lastExecutionUrl = lastExecutionUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractProcess)) return false;
    AbstractProcess process = (AbstractProcess) o;
    return Objects.equals(getName(), process.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    return "" + getName();

  }
}
