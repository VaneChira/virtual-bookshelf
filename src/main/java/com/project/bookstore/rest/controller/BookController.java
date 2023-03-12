package com.project.bookstore.rest.controller;


import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public List<Book> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findBookById(id);
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book){
        bookService.saveBook(book);
        return book;
    }

    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id){
        return bookService.updateBook(book, id);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }
}
