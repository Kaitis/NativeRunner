package com.ak.native_runner_admin.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.ak.native_runner_admin.domain.ProcessGroup;

public interface ProcessGroupRepository extends PagingAndSortingRepository<ProcessGroup, Long> {

  Optional<ProcessGroup> findByName(String name);
}
