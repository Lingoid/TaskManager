package com.example.testsecurity.Services;

import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;




@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(User user) {
            user.setUser_password(passwordEncoder.encode(user.getUser_password()));
            user.setRole("ROLE_USER");
            userRepository.save(user);

    }
}
