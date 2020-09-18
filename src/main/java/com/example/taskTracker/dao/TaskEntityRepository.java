package com.example.taskTracker.dao;

import com.example.taskTracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskEntityRepository extends JpaRepository<Task, Long> {
}
