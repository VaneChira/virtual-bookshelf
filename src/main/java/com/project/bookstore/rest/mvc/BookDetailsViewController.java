package com.project.bookstore.rest.mvc;

import com.project.bookstore.exception.PreconditionFailedException;
import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.FeedbackRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.BookProgressService;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/bookdetails")
public class BookDetailsViewController {
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    BookProgressService bookProgressService;

    Optional<Book> currentBook = Optional.empty();


    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        final var book = bookService.findBookById(id);
        currentBook = Optional.of(book);
        final var userId = getUser().getId();
        final var feedbackKey = new FeedbackKey(userId, id);

        if (feedbackRepository.existsById(feedbackKey)) {
            final var optionalFeedback = feedbackRepository.findById(feedbackKey);
            optionalFeedback.ifPresent(feedback -> model.addAttribute("existingComment", feedback.getComment()));
        }

        boolean isCurrentlyReading = false;
        boolean isCurrentlyReadingOrRead = false;
        final var bookProgressKey = new BookProgressKey(userId, book.getId());


        var bookProgressState = "Set Progress";
        var bookHasState = false;
        var currentPagesRead = 0L;
        if (bookProgressRepository.findById(bookProgressKey).isPresent()) {
            final var bookProgress = bookProgressRepository.findById(bookProgressKey).get();
            if (bookProgress.getProgressPage() != null)
                currentPagesRead = bookProgress.getProgressPage();
            if (bookProgress.getBookState() != null) {
                bookHasState = true;
                switch (bookProgressRepository.findById(bookProgressKey).get().getBookState()) {
                    case 1 -> bookProgressState = "Wishlist";
                    case 2 -> {
                        bookProgressState = "Currently reading";
                        isCurrentlyReading = true;
                        isCurrentlyReadingOrRead = true;
                    }
                    case 3 -> {
                        bookProgressState = "Read";
                        isCurrentlyReadingOrRead = true;
                    }
                    default -> bookProgressState = "Set Progress";
                }
            }
        }
        model.addAttribute("bookStateExists", bookHasState);
        model.addAttribute("progress", bookProgressState);
        model.addAttribute("commentExists", feedbackRepository.existsById(feedbackKey));
        model.addAttribute("book", book);
        model.addAttribute("authors", book.getAuthorInBooks());
        model.addAttribute("genres", bookService.findBookById(id).getGenresInBooks());
        model.addAttribute("averageRating", feedbackRepository.getAverageRating(id));
        model.addAttribute("feedbacks", feedbackRepository.getFeedbacksByBook(id));
        model.addAttribute("numberOfRatings", feedbackRepository.getNumberOfRatings(id));
        model.addAttribute("isCurrentlyReading", isCurrentlyReading);
        model.addAttribute("isCurrentlyReadingOrRead", isCurrentlyReadingOrRead);
        model.addAttribute("percentageRead", (int) currentPagesRead * 100L / book.getPages());

        if (bookService.findBookById(id).getGenresInBooks().stream().findFirst().isPresent()) {
            model.addAttribute("relatedBooks", bookRepository.relatedBooksBasedOnGender(id, bookService.findBookById(id).getGenresInBooks()
                    .stream()
                    .findFirst()
                    .get()
                    .getType()));
        }
        return "bookdetails";
    }

    @ModelAttribute("newrating")
    public Feedback feedback() {
        return new Feedback();
    }

    @ModelAttribute("feedbackkey")
    public FeedbackKey feedbackKey() {
        return new FeedbackKey();
    }

    @PostMapping("/deletebook")
    public String deleteBook() {
        if (currentBook.isPresent()) {
            bookRepository.deleteById(currentBook.get().getId());
            return "redirect:/";
        }
        throw new ResourceNotFoundException("Book id not set", Book.class.getSimpleName());
    }

    @PostMapping("/deletereview")
    public String deleteReview(@ModelAttribute("feedbackkey") FeedbackKey feedbackKey) {
        if (feedbackKey.getUserId() != null && feedbackKey.getBookId() != null && currentBook.isPresent()) {
            if (feedbackRepository.findById(feedbackKey).isPresent()) {
                feedbackRepository.delete(feedbackRepository.findById(feedbackKey).get());
                return "redirect:/bookdetails/" + currentBook.get().getId();
            } else throw new ResourceNotFoundException("Error in getting the feedback", Feedback.class.getSimpleName());
        }
        throw new ResourceNotFoundException("Book id not set", Book.class.getSimpleName());
    }

    @PostMapping("/addreview")
    public String addReview(@ModelAttribute("newrating") Feedback feedback) {
        if (currentBook.isPresent()) {
            feedback.setFeedbackKey(new FeedbackKey(getUser().getId(), currentBook.get().getId()));
            feedback.setBook(currentBook.get());
            feedback.setDate(LocalDate.now());
            feedback.setUser(getUser());

            feedbackRepository.save(feedback);
            return "redirect:/bookdetails/" + currentBook.get().getId();
        }
        throw new ResourceNotFoundException("Book id not set", Book.class.getSimpleName());
    }


    @ModelAttribute("bookProgress")
    public BookProgress pages() {
        return new BookProgress();
    }

    @PostMapping("/update-pages")
    public String updatePages(@ModelAttribute("bookProgress") BookProgress bookProgress) {
        if (currentBook.isPresent()) {
            try {
                bookProgressService.updatePages(getUser().getId(), currentBook.get().getId(), bookProgress.getProgressPage());
            } catch (PreconditionFailedException e) {
                System.out.println("Pages not set");
            }
        }
        return "redirect:/bookdetails/" + currentBook.get().getId();
    }

    private com.project.bookstore.model.User getUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }
}
