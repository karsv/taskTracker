package com.example.taskTracker.controllers;

import com.example.taskTracker.dao.UserEntityRepository;
import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.dto.UserDto;
import com.example.taskTracker.model.TaskStatus;
import com.example.taskTracker.service.TaskService;
import com.example.taskTracker.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Random;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final UserService userService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public InitController(UserService userService,
                          TaskService taskService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            UserDto user = new UserDto();
            user.setEmail("userNo" + i + "@test.com");
            user.setFirstName("fn" + i);
            user.setLastName("ln" + i);
            user.setPassword(passwordEncoder.encode("test" + i));
//            user.setDateOfRegister(LocalDate.of(2020, i / 10 + 1, 1 + i/28));
            userService.create(user);
        }

        for (int i = 0; i < 1000; i++) {
            TaskDto taskDto = new TaskDto();
            taskDto.setStatus(TaskStatus.values()[random.nextInt(TaskStatus.values().length)]
                    .toString());
            taskDto.setDescription("description " + i);
            taskDto.setTitle("title " + i);
            taskService.create((long) random.nextInt(99) + 1, taskDto);
        }
    }
}
