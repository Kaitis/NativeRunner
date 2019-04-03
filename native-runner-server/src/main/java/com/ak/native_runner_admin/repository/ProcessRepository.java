package com.ak.native_runner_admin.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.ak.native_runner_admin.domain.Process;

public interface ProcessRepository extends PagingAndSortingRepository<Process, Long> {

  Optional<Process> findByName(String name);

}
