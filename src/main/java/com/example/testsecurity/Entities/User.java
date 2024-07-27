package com.example.testsecurity.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(max = 100, message = "Слишком длинное имя")
    private String name;
    @Column(name = "user_password")
    @NotEmpty(message = "Пароль должен содержать хотя бы 1 символ")
    @Size(max = 100, message = "Слишком длинное имя")
    private String user_password;
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")

    private List<Task> tasks;

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User() {
    }






    public User getUser(){
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", role='" + role + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
