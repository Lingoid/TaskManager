package com.example.testsecurity.Controllers;


import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class JSController {

    private final UserService userService;

    @Autowired
    public JSController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().
                contentType(MediaType.APPLICATION_JSON).
                body(userService.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody User user){
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
