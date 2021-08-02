package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.UserBookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyBooksViewController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBookInfoService userBookInfoService;

    @GetMapping("/mybooks")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal(); // user from spring security (not model)

        com.project.bookstore.model.User modelUser = userRepository.findByEmail(user.getUsername());

        List<Book> booksByUser = userBookInfoService.findAllBooksByUserEmail(modelUser.getEmail());

        model.addAttribute("loggedUserName", modelUser.getLastName());
        model.addAttribute("books", booksByUser);
        return "mybooks";
    }
}
