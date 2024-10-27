package com.example.taskboard.exception;

/**
 * Custom exception thrown when a task list is not found.
 */
public class TaskListNotFoundException extends RuntimeException {
    public TaskListNotFoundException(String message) {
        super(message);
    }
}
