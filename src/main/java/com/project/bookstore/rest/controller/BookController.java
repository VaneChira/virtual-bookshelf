package com.project.bookstore.rest.controller;


import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/suggestions")
    public Set<Book> suggestions(@RequestParam String keyword){
        return bookService.findSuggestions(keyword);
    }

    @GetMapping("/getBookById/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findBookById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public Book addBook(@Valid @RequestBody Book book){
        bookService.saveBook(book);
        return book;
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id){
        return bookService.updateBook(book, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteBookById/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }
}
