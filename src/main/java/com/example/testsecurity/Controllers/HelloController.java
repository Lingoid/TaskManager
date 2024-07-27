package com.example.testsecurity.Controllers;

import com.example.testsecurity.Entities.Task;
import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Repositories.TaskRepository;
import com.example.testsecurity.Services.TaskService;
import com.example.testsecurity.Services.UserService;
import com.example.testsecurity.util.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloController {
    private final UserService userService;
    private final TaskService taskService;
    private final TaskRepository taskRepository;


    @Autowired
    public HelloController(UserService userService, TaskService taskService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }


    @GetMapping("/adminPage")
    public String adminPage(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {

        userService.delete(id);

        return "redirect:/adminPage";
    }

    @GetMapping("/MyPage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByName(username).orElse(null);


        List<Task> tasks = null;

        if (user != null) {
            tasks = taskService.findByUserId(user.getId());
        } else
            throw new NullPointerException("Такого вользователя нет в БД");

        model.addAttribute("tasks", tasks);
        model.addAttribute("task1", new Task());

        return "MyPage";

    }

    @PostMapping("/MyPage")
    public String createTask(@RequestParam("taskName") String taskName,
                             @RequestParam("taskDescription") String taskDescription,
                             Principal principal) {

        User user = userService.findByName(principal.getName()).orElse(null);


        Task task = new Task(taskName, taskDescription, TaskStatus.Doing, LocalDateTime.now());
        task.setUser(user);


        taskRepository.save(task);

        return "redirect:/MyPage";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId) {
        taskService.deleteTaskByTaskId(taskId);

        return "redirect:/MyPage";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("task") Task task,
                         @RequestParam("taskName") String taskName,
                         @RequestParam("taskDescription") String taskDescription,
                         @RequestParam("taskId") int taskId,
                         Principal principal) {

        User user = userService.findByName(principal.getName()).orElse(null);

        if (user != null) {
            Task existingTask = taskRepository.findById(taskId).orElse(null);
            task.setTask_time(LocalDateTime.now());
            task.setTask_description(taskDescription);
            task.setTask_id(taskId);
            task.setTask_status(TaskStatus.Doing);
            task.setTask_name(taskName);
            if (existingTask != null && existingTask.getUser().getId() == user.getId()) {
                taskService.update(existingTask.getTask_id(),task);

            }
        }

        return "redirect:/MyPage";

    }
}



