package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        List<Book> allBooks = bookService.findAll();
        model.addAttribute("books", allBooks);
        return "index";
    }

}
