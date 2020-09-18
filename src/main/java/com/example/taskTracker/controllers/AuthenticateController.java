package com.example.taskTracker.controllers;

import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.model.User;
import com.example.taskTracker.service.UserService;
import javax.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticateController {
    private final UserService userService;

    public AuthenticateController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody @Valid UserDto userRegistrationDto) {
        try {
            User user = userService.create(userRegistrationDto);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity("There is user with such email!", HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity validationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
