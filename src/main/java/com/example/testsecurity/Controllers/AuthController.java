package com.example.testsecurity.Controllers;

import com.example.testsecurity.Entities.User;
import com.example.testsecurity.Services.RegistrationService;
import com.example.testsecurity.security.PersonDetails;
import com.example.testsecurity.util.UserValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final PersonDetails personDetails;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService, PersonDetails personDetails) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.personDetails = personDetails;
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "RegisterPage";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors())
            return "RegisterPage";

        registrationService.registerUser(user);

        return "redirect:/loginPage";
    }
}
