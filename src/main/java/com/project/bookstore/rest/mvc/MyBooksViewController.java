package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.UserBookInfo;
import com.project.bookstore.repository.UserBookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyBooksViewController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBookRepository userBookRepository;

    @GetMapping("/mybooks")
    public String homeBooks(Model model) {
        com.project.bookstore.model.User modelUser = getUser();
        List<UserBookInfo> userBooks = userBookRepository.findAllCurrentlyReadingAndReadByUser(modelUser.getId());
        model.addAttribute("books", userBooks.stream().map(UserBookInfo::getBook).collect(Collectors.toList())); // get all book fields from userBooks list
        return "mybooks";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }
}
