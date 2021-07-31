package com.project.bookstore.rest.controller;


import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public List<Book> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable int id){
        return bookService.findBookById(id);
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book){
        bookService.saveBook(book);
        return book;
    }

    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable int id){
        return bookService.updateBook(book, id);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public void deleteBookById(@PathVariable int id){
        bookService.deleteBookById(id);
    }


}
