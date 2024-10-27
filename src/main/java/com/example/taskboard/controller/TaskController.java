package com.example.taskboard.controller;

import com.example.taskboard.model.Task;
import com.example.taskboard.model.TaskList;
import com.example.taskboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * REST controller for managing tasks and task lists.
 */
@RestController
@RequestMapping("/api")
public class TaskController {
    private static final Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private TaskService taskService;

    /**
     * Retrieves all task lists.
     *
     * @return a list of all task lists
     */
    @GetMapping("/tasklists")
    public List<TaskList> getAllTaskLists() {
        logger.info("GET /api/tasklists - Fetching all task lists");
        return taskService.getAllTaskLists();
    }

    /**
     * Retrieves a specific task by ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return the task with the specified ID
     */
    @GetMapping("/tasks/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        logger.info("GET /tasks/" + taskId + " - Fetching task with ID: " + taskId);
        return taskService.getTaskById(taskId);
    }

    /**
     * Creates a new task list.
     *
     * @param name the name of the new task list
     * @return the created task list
     */
    @PostMapping("/tasklists")
    public TaskList createTaskList(@RequestParam String name) {
        logger.info("POST /api/tasklists - Creating a new task list with name: " + name);
        return taskService.createTaskList(name);
    }

    /**
     * Adds a new task to an existing task list.
     *
     * @param listId the ID of the task list
     * @param name the name of the new task
     * @param description the description of the new task
     * @return the created task
     */
    @PostMapping("/tasklists/{listId}/tasks")
    public Task addTaskToList(@PathVariable Long listId, @RequestParam String name, @RequestParam String description) {
        logger.info("POST /api/tasklists/" + listId + "/tasks - Adding a new task to list ID: " + listId);
        return taskService.addTaskToList(listId, name, description);
    }

    /**
     * Updates an existing task.
     *
     * @param taskId the ID of the task to update
     * @param name the new name of the task
     * @param description the new description of the task
     * @return the updated task
     */
    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestParam String name, @RequestParam String description) {
        logger.info("PUT /tasks/" + taskId + " - Updating task ID: " + taskId);
        return taskService.updateTask(taskId, name, description);
    }

    /**
     * Deletes a task.
     *
     * @param taskId the ID of the task to delete
     */
    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        logger.info("DELETE /tasks/" + taskId + " - Deleting task ID: " + taskId);
        taskService.deleteTask(taskId);
    }

    /**
     * Deletes a task list and all its tasks.
     *
     * @param listId the ID of the task list to delete
     */
    @DeleteMapping("/tasklists/{listId}")
    public void deleteTaskList(@PathVariable Long listId) {
        logger.info("DELETE /api/tasklists/" + listId + " - Deleting task list ID: " + listId);
        taskService.deleteTaskList(listId);
    }

    /**
     * Moves a task to a different task list.
     *
     * @param taskId the ID of the task to move
     * @param newListId the ID of the new task list
     * @return the moved task
     */
    @PutMapping("/tasks/{taskId}/move")
    public Task moveTaskToList(@PathVariable Long taskId, @RequestParam Long newListId) {
        logger.info("PUT /tasks/" + taskId + "/move - Moving task ID: " + taskId + " to list ID: " + newListId);
        return taskService.moveTaskToList(taskId, newListId);
    }
}
