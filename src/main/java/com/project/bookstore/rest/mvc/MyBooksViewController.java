package com.project.bookstore.rest.mvc;

import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyBooksViewController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/mybooks")
    public String homeBooks(Model model) {
        Long userId = getUser().getId();
        model.addAttribute("allbooks", bookRepository.findAllCurrentlyReadingAndReadByUser(userId));
        model.addAttribute("readbooks", bookRepository.findAllReadByUser(userId));
        model.addAttribute("readingbooks", bookRepository.findAllCurrentlyReadingByUser(userId));

        return "mybooks";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }

}
