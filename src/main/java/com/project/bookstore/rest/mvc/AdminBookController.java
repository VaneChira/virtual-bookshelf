package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.AuthorRepository;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Admin book management (list/add/edit/delete), server-rendered via Thymeleaf.
 * Everything here lives under /admin/**, already restricted to ROLE_ADMIN by
 * SecurityConfiguration, so no per-method @PreAuthorize is needed on top of that.
 */
@Controller
public class AdminBookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CloudinaryService cloudinaryService;

    public AdminBookController(BookService bookService, AuthorRepository authorRepository, GenreRepository genreRepository, CloudinaryService cloudinaryService) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @ModelAttribute("authors")
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @ModelAttribute("genres")
    public List<Genre> genres() {
        return genreRepository.findAll();
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/admin/books/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    //  if the admin submits the form without picking a file, coverImage is usually not null — HTML file inputs still submit an empty multipart part
    //  (size 0) even when nothing's selected -> hence the two condition check
    @PostMapping("/admin/books")
    public String createBook(@Valid @ModelAttribute Book book,
                              BindingResult bindingResult,
                              @RequestParam(required = false) List<Long> authorIds,
                              @RequestParam(required = false) List<Long> genreIds,
                              @RequestParam(required = false) MultipartFile coverImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/book-form";
        }
        book.setAuthorInBooks(resolveAuthors(authorIds));
        book.setGenresInBooks(resolveGenres(genreIds));
        if(coverImage != null && !coverImage.isEmpty()) {
            book.setImageUrl(cloudinaryService.uploadImage(coverImage));
        }
        bookService.saveBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/admin/books/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "admin/book-form";
    }

    @PostMapping("/admin/books/{id}")
    public String updateBook(@PathVariable Long id,
                              @Valid @ModelAttribute Book book,
                              BindingResult bindingResult,
                              @RequestParam(required = false) List<Long> authorIds,
                              @RequestParam(required = false) List<Long> genreIds,
                              @RequestParam(required = false) MultipartFile coverImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/book-form";
        }
        book.setAuthorInBooks(resolveAuthors(authorIds));
        book.setGenresInBooks(resolveGenres(genreIds));
        if (coverImage != null && !coverImage.isEmpty()) {
            book.setImageUrl(cloudinaryService.uploadImage(coverImage));
        }
        bookService.updateBook(book, id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/admin";
    }

    private Set<Author> resolveAuthors(List<Long> authorIds) {
        if (authorIds == null) {
            return new HashSet<>();
        }
        return new HashSet<>(authorRepository.findAllById(authorIds));
    }

    private Set<Genre> resolveGenres(List<Long> genreIds) {
        if (genreIds == null) {
            return new HashSet<>();
        }
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }
}
