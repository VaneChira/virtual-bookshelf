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
import org.springframework.security.core.Authentication;
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
        Book book = bookService.findBookById(id);
        currentBook = Optional.of(book);

        Long userId = getUser().getId();

        FeedbackKey feedbackKey = new FeedbackKey(userId, id);
        boolean commentExists = feedbackRepository.existsById(feedbackKey);
        if (commentExists) {
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackKey);
            optionalFeedback.ifPresent(feedback -> model.addAttribute("existingComment", feedback.getComment()));
        }

        boolean isCurrentlyReading = false;
        boolean isCurrentlyReadingOrRead = false;
        BookProgressKey bookProgressKey = new BookProgressKey(userId, book.getId());


        String bookProgressState = "Set Progress";
        boolean bookHasState = false;
        Long currentPagesRead = 0L;
        if (bookProgressRepository.findById(bookProgressKey).isPresent()) {
            BookProgress bookProgress = bookProgressRepository.findById(bookProgressKey).get();
            if (bookProgress.getProgressPage() != null)
                currentPagesRead = bookProgress.getProgressPage();
            if (bookProgress.getBookState() != null) {
                bookHasState = true;
                Integer state = bookProgressRepository.findById(bookProgressKey).get().getBookState();
                switch (state) {
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
        model.addAttribute("commentExists", commentExists);
        model.addAttribute("book", book);
        model.addAttribute("authors", book.getAuthorInBooks());
        model.addAttribute("genres", bookService.findBookById(id).getGenresInBooks());
        model.addAttribute("averageRating", feedbackRepository.getAverageRating(id));
        model.addAttribute("feedbacks", feedbackRepository.getFeedbacksByBook(id));
        model.addAttribute("numberOfRatings", feedbackRepository.getNumberOfRatings(id));
        model.addAttribute("isCurrentlyReading", isCurrentlyReading);
        model.addAttribute("isCurrentlyReadingOrRead", isCurrentlyReadingOrRead);
        float percentRead = (currentPagesRead * 100 / book.getPages());
        model.addAttribute("percentageRead", (int) percentRead);

        if (bookService.findBookById(id).getGenresInBooks().stream().findFirst().isPresent()) {
            model.addAttribute("relatedBooks", bookRepository.relatedBooksBasedOnGender(id, bookService.findBookById(id).getGenresInBooks().stream().findFirst().get().getType()));
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
                Feedback feedbackToDelete = feedbackRepository.findById(feedbackKey).get();
                feedbackRepository.delete(feedbackToDelete);
                return "redirect:/bookdetails/" + currentBook.get().getId();
            } else throw new ResourceNotFoundException("Error in getting the feedback", Feedback.class.getSimpleName());
        }
        throw new ResourceNotFoundException("Book id not set", Book.class.getSimpleName());
    }

    @PostMapping("/addreview")
    public String addReview(@ModelAttribute("newrating") Feedback feedback) {
        if (currentBook.isPresent()) {

            Book book = currentBook.get();
            com.project.bookstore.model.User user = getUser();

            FeedbackKey feedbackKey = new FeedbackKey(user.getId(), book.getId());
            feedback.setFeedbackKey(feedbackKey);
            feedback.setBook(book);
            feedback.setDate(LocalDate.now());
            feedback.setUser(user);

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

            Book book = currentBook.get();
            com.project.bookstore.model.User user = getUser();

            try {
                bookProgressService.updatePages(user.getId(), book.getId(), bookProgress.getProgressPage());
            } catch (PreconditionFailedException e) {
                System.out.println("Pages not set");
            }
        }
        return "redirect:/bookdetails/" + currentBook.get().getId();
    }


    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }

}
