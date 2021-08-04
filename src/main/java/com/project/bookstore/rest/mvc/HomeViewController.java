package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    BookService bookService;

    @GetMapping("/")
    public String viewInHomePage(Model model, @Param("keyword") String keyword) {
        List<Book> listBooks = bookService.listAll(keyword);
        model.addAttribute("books", listBooks);
        model.addAttribute("keyword", keyword);

        return "index";
    }

}
