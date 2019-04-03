package com.ak.native_runner_admin.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class AbtractDomainObject {

  @CreatedDate
  LocalDateTime dateCreated;

  @LastModifiedDate
  LocalDateTime dateLastModified;

  @PrePersist
  @PreUpdate
  private void updateTimestamps(){
    if(dateCreated == null)
      dateCreated = LocalDateTime.now();
    dateLastModified = LocalDateTime.now();
  }

  public abstract Long getId();

  public LocalDateTime getDateCreated() {
    return dateCreated;
  }

  public LocalDateTime getDateLastModified() {
    return dateLastModified;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbtractDomainObject)) return false;
    AbtractDomainObject process = (AbtractDomainObject) o;
    return Objects.equals(getId(), process.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString () {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
