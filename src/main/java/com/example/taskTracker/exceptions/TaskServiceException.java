package com.example.taskTracker.exceptions;

public class TaskServiceException extends RuntimeException {
    public TaskServiceException(String message) {
        super(message);
    }
}
