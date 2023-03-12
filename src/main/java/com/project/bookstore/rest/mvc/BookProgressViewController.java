package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgress;
import com.project.bookstore.model.BookProgressKey;
import com.project.bookstore.model.BookStateEnum;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book-progress")
public class BookProgressViewController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add-to-wishlist")
    public String addToWishlist(@ModelAttribute("book") Book book) {
        final var bookProgressKey = new BookProgressKey(getLoggedUser().getId(), book.getId());
        final var bookProgressOpt = bookProgressRepository.findById(bookProgressKey);
        BookProgress bookProgress;
        if (bookProgressOpt.isPresent()) {
            bookProgress = bookProgressOpt.get();
        } else {
            bookProgress = new BookProgress();
            bookProgress.setBookProgressKey(bookProgressKey);
            bookProgress.setBook(bookRepository.findById(book.getId()).get());
            bookProgress.setUser(getLoggedUser());
        }
        bookProgress.setBookState(BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));
        bookProgress.setProgressPage(0L);
        bookProgressRepository.save(bookProgress);
        return "redirect:/bookdetails/" + book.getId();
    }

    @PostMapping("/add-to-currently-reading")
    public String addToCurrentlyReading(@ModelAttribute("book") Book book) {
        final var bookProgressKey = new BookProgressKey(getLoggedUser().getId(), book.getId());
        final var bookProgressOpt = bookProgressRepository.findById(bookProgressKey);
        BookProgress bookProgress;
        if (bookProgressOpt.isPresent()) {
            bookProgress = bookProgressOpt.get();
        } else {
            bookProgress = new BookProgress();
            bookProgress.setBookProgressKey(bookProgressKey);
            bookProgress.setBook(bookRepository.findById(book.getId()).get());
            bookProgress.setUser(getLoggedUser());
        }
        bookProgress.setBookState(BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));
        bookProgressRepository.save(bookProgress);

        return "redirect:/bookdetails/" + book.getId();
    }

    @PostMapping("/add-to-read")
    public String addToRead(@ModelAttribute("book") Book book) {
        final var bookProgressKey = new BookProgressKey(getLoggedUser().getId(), book.getId());
        final var bookProgressOpt = bookProgressRepository.findById(bookProgressKey);
        BookProgress bookProgress;
        if (bookProgressOpt.isPresent()) {
            bookProgress = bookProgressOpt.get();
        } else {
            bookProgress = new BookProgress();
            bookProgress.setBookProgressKey(bookProgressKey);
            bookProgress.setBook(bookRepository.findById(book.getId()).get());
            bookProgress.setUser(getLoggedUser());
        }
        bookProgress.setBookState(BookStateEnum.fromEnumToInt(BookStateEnum.READ));
        bookProgress.setProgressPage(book.getPages());
        bookProgressRepository.save(bookProgress);
        return "redirect:/bookdetails/" + book.getId();
    }

    @PostMapping("/reset-progress")
    public String resetProgress(@ModelAttribute("book") Book book) {
        final var bookProgressOpt = bookProgressRepository.findById(new BookProgressKey(getLoggedUser().getId(), book.getId()));
        if (bookProgressOpt.isPresent()){
            final var bookProgress = bookProgressOpt.get();
            bookProgress.setBookState(null);
            bookProgress.setProgressPage(null);
            bookProgressRepository.save(bookProgress);
        }
        return "redirect:/bookdetails/" + book.getId();
    }

    private com.project.bookstore.model.User getLoggedUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }
}
