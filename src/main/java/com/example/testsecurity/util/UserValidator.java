package com.example.testsecurity.util;

import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findByName(user.getName()).isPresent()){
            errors.rejectValue("name", "user.name.unique","Это имя пользователя занято");
        }

    }
}
