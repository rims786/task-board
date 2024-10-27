package com.example.taskboard.service;

import com.example.taskboard.exception.TaskListNotFoundException;
import com.example.taskboard.exception.TaskNotFoundException;
import com.example.taskboard.model.Task;
import com.example.taskboard.model.TaskList;
import com.example.taskboard.repository.TaskListRepository;
import com.example.taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service class for managing tasks and task lists.
 */
@Service
public class TaskService {
    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    /**
     * Retrieves all task lists.
     *
     * @return a list of all task lists
     */
    public List<TaskList> getAllTaskLists() {
        logger.info("Fetching all task lists");
        return taskListRepository.findAll();
    }

    /**
     * Creates a new task list.
     *
     * @param name the name of the new task list
     * @return the created task list
     */
    public TaskList createTaskList(String name) {
        logger.info("Creating a new task list with name: " + name);
        TaskList taskList = new TaskList();
        taskList.setName(name);
        return taskListRepository.save(taskList);
    }

    /**
     * Adds a new task to an existing task list.
     *
     * @param listId the ID of the task list
     * @param name the name of the new task
     * @param description the description of the new task
     * @return the created task
     */
    public Task addTaskToList(Long listId, String name, String description) {
        logger.info("Adding a new task to list ID: " + listId);
        Optional<TaskList> taskListOptional = taskListRepository.findById(listId);
        if (taskListOptional.isPresent()) {
            Task task = new Task();
            task.setName(name);
            task.setDescription(description);
            task.setTaskList(taskListOptional.get());
            return taskRepository.save(task);
        } else {
            throw new TaskListNotFoundException("Task list with ID " + listId + " not found");
        }
    }

    /**
     * Updates an existing task.
     *
     * @param taskId the ID of the task to update
     * @param name the new name of the task
     * @param description the new description of the task
     * @return the updated task
     */
    public Task updateTask(Long taskId, String name, String description) {
        logger.info("Updating task ID: " + taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setName(name);
            task.setDescription(description);
            return taskRepository.save(task);
        } else {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found");
        }
    }

    /**
     * Deletes a task.
     *
     * @param taskId the ID of the task to delete
     */
    public void deleteTask(Long taskId) {
        logger.info("Deleting task ID: " + taskId);
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found");
        }
    }

    /**
     * Deletes a task list and all its tasks.
     *
     * @param listId the ID of the task list to delete
     */
    public void deleteTaskList(Long listId) {
        logger.info("Deleting task list ID: " + listId);
        if (taskListRepository.existsById(listId)) {
            taskListRepository.deleteById(listId);
        } else {
            throw new TaskListNotFoundException("Task list with ID " + listId + " not found");
        }
    }

    /**
     * Moves a task to a different task list.
     *
     * @param taskId the ID of the task to move
     * @param newListId the ID of the new task list
     * @return the moved task
     */
    public Task moveTaskToList(Long taskId, Long newListId) {
        logger.info("Moving task ID: " + taskId + " to list ID: " + newListId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<TaskList> newTaskListOptional = taskListRepository.findById(newListId);
        if (taskOptional.isPresent() && newTaskListOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTaskList(newTaskListOptional.get());
            return taskRepository.save(task);
        } else {
            if (!taskOptional.isPresent()) {
                throw new TaskNotFoundException("Task with ID " + taskId + " not found");
            }
            if (!newTaskListOptional.isPresent()) {
                throw new TaskListNotFoundException("Task list with ID " + newListId + " not found");
            }
            return null; // This line should never be reached
        }
    }

    /**
     * Retrieves a specific task by ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return the task with the specified ID
     */
    public Task getTaskById(Long taskId) {
        logger.info("Fetching task with ID: " + taskId);
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found"));
    }

}

