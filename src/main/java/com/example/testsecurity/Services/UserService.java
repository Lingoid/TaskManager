package com.example.testsecurity.Services;

import com.example.testsecurity.Entities.Task;
import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Repositories.TaskRepository;
import com.example.testsecurity.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByName(String username){
        return userRepository.findByName(username);
    }
    @Transactional
    public void delete(int id){
        userRepository.deleteById(id);
    }
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}
