package com.example.taskTracker.controllers;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.exceptions.TaskServiceException;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.TaskStatus;
import com.example.taskTracker.service.TaskService;
import com.example.taskTracker.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity getTasks(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int numberOnPage,
                            @RequestParam(defaultValue = "status") String orderBy) {
        Page<Task> tasks;
        if (orderBy.equals("status")) {
            tasks = taskService.getAllByStatus(page, numberOnPage);
            return new ResponseEntity(tasks, HttpStatus.OK);
        } else if (orderBy.equals("userAsc")) {
            tasks = taskService.getAllByNewestUsers(page, numberOnPage);
            return new ResponseEntity(tasks, HttpStatus.OK);
        } else if (orderBy.equals("userDesc")) {
            tasks = taskService.getAllByOldestUsers(page, numberOnPage);
            return new ResponseEntity(tasks, HttpStatus.OK);
        }
        return new ResponseEntity("There isn't such sorting parameter!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    ResponseEntity createTask(@RequestBody TaskDto taskDto) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskService.create(userService.
                getUserByEmail(userDetails.getUsername()).
                getId(), taskDto);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity updateTask(@PathVariable Long id,
                              @RequestBody(required = false) TaskDto taskDto,
                              @RequestParam TaskStatus status) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (taskDto != null) {
            taskDto.setStatus(status.toString());
            Task task = taskService.update(id,
                    userService.getUserByEmail(userDetails.getUsername()).getId(),
                    taskDto);
            return new ResponseEntity(task, HttpStatus.OK);
        } else {
            Task task = taskService.update(id, status);
            return new ResponseEntity(task, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteTask(@PathVariable(value = "id") Long id) {
        taskService.delete(id);
        return new ResponseEntity("Task with id: " + id + " was deleted!", HttpStatus.OK);
    }

    @ExceptionHandler(TaskServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity userServiceError(TaskServiceException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
