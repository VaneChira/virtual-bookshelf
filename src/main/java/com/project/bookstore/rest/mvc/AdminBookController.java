package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.AuthorRepository;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    public AdminBookController(BookService bookService, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/admin/books/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/book-form";
    }

    @PostMapping("/admin/books")
    public String createBook(@ModelAttribute Book book,
                              @RequestParam(required = false) List<Long> authorIds,
                              @RequestParam(required = false) List<Long> genreIds) {
        book.setAuthorInBooks(resolveAuthors(authorIds));
        book.setGenresInBooks(resolveGenres(genreIds));
        bookService.saveBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/admin/books/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/book-form";
    }

    @PostMapping("/admin/books/{id}")
    public String updateBook(@PathVariable Long id,
                              @ModelAttribute Book book,
                              @RequestParam(required = false) List<Long> authorIds,
                              @RequestParam(required = false) List<Long> genreIds) {
        book.setAuthorInBooks(resolveAuthors(authorIds));
        book.setGenresInBooks(resolveGenres(genreIds));
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
