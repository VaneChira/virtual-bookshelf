package com.project.bookstore.rest.mvc;

import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
    public String viewInHomePage(Model model, @Param("keyword") String keyword) {
        Long userId = getUser().getId();
        model.addAttribute("books", bookService.listAll(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("wishlistBooks", bookRepository.findAllWishlistByUser(userId));
        model.addAttribute("numberOfWishlistBooks", bookProgressRepository.getNumberOfWishlistBooks(userId));
        model.addAttribute("numberOfCurrentlyReadingBooks", bookProgressRepository.getNumberOfCurrentlyReadingBooks(userId));
        model.addAttribute("numberOfReadBooks", bookProgressRepository.getNumberOfReadBooks(userId));
        model.addAttribute("readingbooks", bookRepository.findAllCurrentlyReadingByUser(userId));
        model.addAttribute("recommendationBooks", bookService.getStatelessBooksByUserId(userId));


        model.addAttribute("topUsersByBooksRead", userService.getTopUsers().entrySet()
                .stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));

        return "index";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }

}
