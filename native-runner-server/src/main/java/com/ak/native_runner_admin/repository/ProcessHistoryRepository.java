package com.ak.native_runner_admin.repository;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.ak.native_runner_admin.domain.ProcessHistory;

public interface ProcessHistoryRepository extends PagingAndSortingRepository<ProcessHistory, Long> {

  @Query("Select p from ProcessHistory p where p.process.standalone = 1")
  List<ProcessHistory> findStandalone();

  @Query("Select p from ProcessHistory p where p.process.standalone = 1")
  Page<ProcessHistory> findStandalone(Pageable pageable);
}
