package com.project.bookstore.rest.mvc;

import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal(); // user from spring security (not model)

        com.project.bookstore.model.User modelUser = userRepository.findByEmail(user.getUsername());

        model.addAttribute("loggedUserName", modelUser.getLastName());
        return "index";
    }

}
