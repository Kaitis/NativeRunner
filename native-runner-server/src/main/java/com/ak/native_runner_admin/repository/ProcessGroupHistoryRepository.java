package com.ak.native_runner_admin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.ak.native_runner_admin.domain.ProcessGroupHistory;

public interface ProcessGroupHistoryRepository extends PagingAndSortingRepository<ProcessGroupHistory, Long> {


}
