package com.example.taskboard.exception;

/**
 * Custom exception thrown when a task is not found.
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
