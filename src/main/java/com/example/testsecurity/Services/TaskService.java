package com.example.testsecurity.Services;

import com.example.testsecurity.Entities.Task;
import com.example.testsecurity.Repositories.TaskRepository;
import com.example.testsecurity.util.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findByUserId(int userId) {
        return taskRepository.findByUserId(userId);
    }

    public boolean validateTimeFormat(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Transactional
    public void update(int id, Task task) {
        Task taskToBEUpdated = taskRepository.findById(id).orElse(null);
        if (taskToBEUpdated != null) {

            taskToBEUpdated.setTask_name(task.getTask_name());
            taskToBEUpdated.setTask_description(task.getTask_description());
            taskToBEUpdated.setTask_status(task.getTask_status());
            taskToBEUpdated.setTask_time(task.getTask_time());

            taskRepository.save(taskToBEUpdated);
        }
    }
    @Transactional
    public void deleteTaskByTaskId(int id){
        taskRepository.deleteTaskByTaskId(id);
    }


}
