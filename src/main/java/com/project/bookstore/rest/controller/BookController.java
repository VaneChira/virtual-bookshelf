package com.project.bookstore.rest.controller;


import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    private final CloudinaryService cloudinaryService;

    public BookController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

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

    @PostMapping("/upload-cover")
    public ResponseEntity<String> uploadCoverImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file);
            // Save imageUrl to your Book entity / database here
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload image.");
        }
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
