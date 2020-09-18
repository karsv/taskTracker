package com.example.taskTracker.util.mapper;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.TaskStatus;
import java.time.LocalDate;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

    default Task taskDtoToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setIdOfCreator(taskDto.getUserId());
        task.setDateOfCreated(LocalDate.now());
        task.setDateOfStatusChange(LocalDate.now());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.VIEW);
        return task;
    }
}
