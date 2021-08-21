package com.project.bookstore.rest.mvc;


import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgressKey;
import com.project.bookstore.model.Feedback;
import com.project.bookstore.model.FeedbackKey;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.FeedbackRepository;
import com.project.bookstore.repository.UserRepository;
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

    Optional<Book> currentBook = Optional.empty();


    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findBookById(id);
        currentBook = Optional.of(book);

        Long userId = getUser().getId();

        FeedbackKey feedbackKey = new FeedbackKey(userId ,id);
        boolean commentExists = feedbackRepository.existsById(feedbackKey);

        if(commentExists){
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackKey);
            optionalFeedback.ifPresent(feedback -> model.addAttribute("existingComment", feedback.getComment()));
        }

        model.addAttribute("commentExists", commentExists);
        model.addAttribute("book", book);
        model.addAttribute("authors", book.getAuthorInBooks());
        model.addAttribute("genres", bookService.findBookById(id).getGenresInBooks());
        model.addAttribute("averageRating", feedbackRepository.getAverageRating(id));
        model.addAttribute("feedbacks", feedbackRepository.getFeedbacksByBook(id));
        model.addAttribute("numberOfRatings", feedbackRepository.getNumberOfRatings(id));

        BookProgressKey bookProgressKey = new BookProgressKey(userId, book.getId());
        String bookProgressState;
        if(bookProgressRepository.findById(bookProgressKey).isPresent()) {
            Integer bookProgress = bookProgressRepository.findById(bookProgressKey).get().getBookState();
            switch(bookProgress){
                case 1:
                    bookProgressState = "Wishlist";
                    break;
                case 2:
                    bookProgressState = "Currently reading";
                    break;
                case 3:
                    bookProgressState = "Read";
                    break;
                default:
                    bookProgressState = "Set Progress";
                    break;
            }
            model.addAttribute("progress", bookProgressState);
        }
        else{
            bookProgressState = "Set Progress";
            model.addAttribute("progress", bookProgressState);
        }

        if(bookService.findBookById(id).getGenresInBooks().stream().findFirst().isPresent()) {
            model.addAttribute("relatedBooks", bookRepository.relatedBooksBasedOnGender(id, bookService.findBookById(id).getGenresInBooks().stream().findFirst().get().getType()));
        }
        return "bookdetails";
    }

    @ModelAttribute("newrating")
    public Feedback feedback() {
        return new Feedback();
    }


    @PostMapping
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

// post method for pages


    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }

}
