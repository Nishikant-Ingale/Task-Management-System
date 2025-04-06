package com.nishikant.task_service.repository;

import com.nishikant.task_service.dto.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository  extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUser(String user);
}
