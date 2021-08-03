package com.project.bookstore.rest.mvc;


import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class BookDetailsViewController {

    @Autowired
    BookService bookService;

    @GetMapping("/bookdetails/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "bookdetails";
    }
}
