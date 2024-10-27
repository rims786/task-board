package com.example.taskboard.repository;

import com.example.taskboard.model.Task;
import com.example.taskboard.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Task entities.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
