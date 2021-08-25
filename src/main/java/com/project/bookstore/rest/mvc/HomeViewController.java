package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgress;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @Autowired
    BookService bookService;

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;



    @GetMapping("/")
    public String viewInHomePage(Model model, @Param("keyword") String keyword) {
        com.project.bookstore.model.User modelUser = getUser();
        List<Book> listBooks = bookService.listAll(keyword);
        model.addAttribute("books", listBooks);
        model.addAttribute("keyword", keyword);
        List<BookProgress> bookProgresses = bookProgressRepository.findAllWishlistByUser(modelUser.getId());
        model.addAttribute("wishlistBooks", bookProgresses.stream().map(BookProgress::getBook).collect(Collectors.toList()));
        model.addAttribute("numberOfWishlistBooks", bookProgressRepository.getNumberOfWishlistBooks(modelUser.getId()));
        model.addAttribute("numberOfCurrentlyReadingBooks", bookProgressRepository.getNumberOfCurrentlyReadingBooks(modelUser.getId()));
        model.addAttribute("numberOfReadBooks", bookProgressRepository.getNumberOfReadBooks(modelUser.getId()));
        List<BookProgress> readingBooks = bookProgressRepository.findAllCurrentlyReadingByUser(modelUser.getId());
        model.addAttribute("readingbooks", readingBooks.stream().map(BookProgress::getBook).collect(Collectors.toList()));
//        model.addAttribute("recommendationsBooks", bookRepository.booksForRecommendations(modelUser.getId()));



        return "index";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)

        return userRepository.findByEmail(user.getUsername());
    }

}
