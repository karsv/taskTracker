package com.example.taskTracker.controllers;

import com.example.taskTracker.dao.UserEntityRepository;
import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.model.TaskStatus;
import com.example.taskTracker.model.User;
import com.example.taskTracker.service.TaskService;
import com.example.taskTracker.service.UserService;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserEntityRepository userEntityRepository;

    public InitController(UserService userService,
                          TaskService taskService,
                          UserEntityRepository userEntityRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.userEntityRepository = userEntityRepository;
    }

    @PostConstruct
    private void init() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setEmail("userNo" + i + "@test.com");
            user.setFirstName("fn" + i);
            user.setLastName("ln" + i);
            user.setPassword("test");
            user.setDateOfRegister(LocalDate.of(2020, i / 10 + 1, 1 + i/28));
            userEntityRepository.save(user);
        }

        for (int i = 0; i < 1000; i++) {
            TaskDto taskDto = new TaskDto();
            taskDto.setStatus(TaskStatus.values()[random.nextInt(TaskStatus.values().length)]
                    .toString());
            taskDto.setDescription("description " + i);
            taskDto.setTitle("title " + i);
            taskService.create((long) random.nextInt(99) + 1 ,taskDto);
        }
    }
}
