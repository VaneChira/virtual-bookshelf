package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.AuthorRepository;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.CloudinaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final int PAGE_SIZE = 10;

    @GetMapping("/admin")
    public String dashboard(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "newest") String sort,
                             Model model) {
        final var direction = "oldest".equals(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        final var books = bookService.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "id")));
        model.addAttribute("books", books);
        model.addAttribute("sort", sort);
        model.addAttribute("totalBooks", books.getTotalElements());
        model.addAttribute("totalAuthors", authorRepository.count());
        model.addAttribute("totalGenres", genreRepository.count());
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
                              @RequestParam(required = false) String authorNames,
                              @RequestParam(required = false) String genreNames,
                              @RequestParam(required = false) MultipartFile coverImage,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/book-form";
        }
        book.setAuthorInBooks(resolveAuthors(authorNames));
        book.setGenresInBooks(resolveGenres(genreNames));
        if(coverImage != null && !coverImage.isEmpty()) {
            book.setImageUrl(cloudinaryService.uploadImage(coverImage));
        }
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "\"" + book.getBookTitle() + "\" was added successfully.");
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
                              @RequestParam(required = false) String authorNames,
                              @RequestParam(required = false) String genreNames,
                              @RequestParam(required = false) MultipartFile coverImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/book-form";
        }
        book.setAuthorInBooks(resolveAuthors(authorNames));
        book.setGenresInBooks(resolveGenres(genreNames));
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

    private Set<Author> resolveAuthors(String authorNames) {
        final var authors = new HashSet<Author>();
        for (String name : splitNames(authorNames)) {
            final var existing = authorRepository.findByName(name);
            if (existing != null) {
                authors.add(existing);
            } else {
                final var newAuthor = new Author();
                newAuthor.setName(name);
                authors.add(authorRepository.save(newAuthor));
            }
        }
        return authors;
    }

    private Set<Genre> resolveGenres(String genreNames) {
        final var genres = new HashSet<Genre>();
        for (String type : splitNames(genreNames)) {
            final var existing = genreRepository.findByType(type);
            if (existing != null) {
                genres.add(existing);
            } else {
                final var newGenre = new Genre();
                newGenre.setType(type);
                genres.add(genreRepository.save(newGenre));
            }
        }
        return genres;
    }

    private List<String> splitNames(String commaSeparated) {
        if (commaSeparated == null || commaSeparated.isBlank()) {
            return List.of();
        }
        return Arrays.stream(commaSeparated.split(","))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}
