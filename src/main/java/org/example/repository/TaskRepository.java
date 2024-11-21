package org.example.repository;

import org.example.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TaskRepository extends JpaRepository<Task, Long>{
    @Query(value = "SELECT t FROM Task As t WHERE (t.assigneeId = (?1) OR (?1) IS NULL) AND\n" +
            "(t.eventId = (?2) OR (?2) IS NULL)")
    Page<Task> findByFilters(Long assigneeId, Long eventId,Long authorId, Pageable pageable);
}
