package com.example.taskTracker.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDate dateOfCreated;

    public Task() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long idOfCreator) {
        this.userId = idOfCreator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDate dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                Objects.equals(dateOfCreated, task.dateOfCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, dateOfCreated);
    }
}
