package com.example.testsecurity.Entities;

import com.example.testsecurity.util.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "task_name")
    @Size(max = 100, message = "Слишком длинное название")
    private String task_name;
    @Column(name = "task_description")
    private String task_description;
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus task_status;
    @Column(name = "task_time")
    private LocalDateTime task_time;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTask_id() {
        return taskId;
    }

    public void setTask_id(int task_id) {
        this.taskId = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public TaskStatus getTask_status() {
        return task_status;
    }

    public void setTask_status(TaskStatus task_status) {
        this.task_status = task_status;
    }

    public LocalDateTime getTask_time() {
        return task_time;
    }

    public void setTask_time(LocalDateTime task_time) {
        this.task_time = task_time;
    }

    public Task() {
    }

    public Task(String task_name, String task_description, TaskStatus task_status, LocalDateTime task_time) {
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_status = task_status;
        this.task_time = task_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && task_status == task.task_status && Objects.equals(task_name, task.task_name) && Objects.equals(task_description, task.task_description) && Objects.equals(task_time, task.task_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, task_name, task_description, task_status, task_time);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task_name='" + task_name + '\'' +
                ", task_description='" + task_description + '\'' +
                ", task_status=" + task_status;


    }
}
