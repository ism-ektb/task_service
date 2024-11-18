package org.example.repository;

import org.example.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TaskRepository extends JpaRepository<Task, Long>{
    @Query("SELECT t FROM Task As t WHERE "
            + "t.assigneeId = (?1) OR (?1) IS NULL "
            + "AND t.eventId = (?2) OR (?2) IS NULL "
            + "AND t.authorId = (?3) OR (?3) IS NULL")
    Page<Task> findByFilters(Long assigneeId, Long eventId,Long authorId, Pageable pageable);

    @Override
    Page<Task> findAll(Pageable pageable);
}
