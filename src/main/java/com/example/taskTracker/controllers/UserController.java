package com.example.taskTracker.controllers;

import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.exceptions.UserServiceException;
import com.example.taskTracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer usersOnPage) {
        return new ResponseEntity(userService.getAllUsers(page, usersOnPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity(userService.getUserResponseDtoById(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id") Long id,
                                     @RequestBody UserDto userDto) {

        return new ResponseEntity(userService.update(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id) {
        userService.delete(id);
        return new ResponseEntity("User with id: " + id + " was deleted!", HttpStatus.OK);
    }

    @ExceptionHandler(UserServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity userServiceError(UserServiceException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
