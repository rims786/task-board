package com.example.taskboard.repository;

import com.example.taskboard.model.Task;
import com.example.taskboard.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Repository interface for TaskList entities.
 */
@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {

}