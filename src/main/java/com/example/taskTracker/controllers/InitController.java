package com.example.taskTracker.controllers;

import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.model.User;
import com.example.taskTracker.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class InitController {
    private final UserService userService;

    public InitController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void init(){
        for (int i = 0; i < 100; i++) {
            UserDto user = new UserDto();
            user.setEmail("userNo"+ i+"test.com");
            user.setFirstName("fn" + i);
            user.setLastName("ln" + i);
            user.setPassword("test");
            user.setRepeatedPassword("test");
            userService.create(user);
        }
    }
}
