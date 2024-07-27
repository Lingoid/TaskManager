package com.example.testsecurity.Repositories;

import com.example.testsecurity.Entities.Task;
import com.example.testsecurity.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserId(int user_id);

    void deleteTaskByTaskId(int id);

}
