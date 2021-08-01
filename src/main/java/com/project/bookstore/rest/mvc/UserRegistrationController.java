package com.project.bookstore.rest.mvc;

import com.project.bookstore.security.UserRegistrationFormEntity;
import com.project.bookstore.security.UserSecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private UserSecurityService userSecurityService;

    public UserRegistrationController(UserSecurityService userSecurityService) {
        super();
        this.userSecurityService = userSecurityService;
    }

    @ModelAttribute("user")
    public UserRegistrationFormEntity userRegistrationEntity() {
        return new UserRegistrationFormEntity();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationFormEntity userForm) {
        userSecurityService.save(userForm);
        return "redirect:/registration?success";
    }
}
