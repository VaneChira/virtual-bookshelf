package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookStateEnum;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.BookProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    BookProgressService bookProgressService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add-to-wishlist")
    public String addToWishlist(@ModelAttribute("book") Book book) {
        com.project.bookstore.model.User loggedUser = getLoggedUser();
        bookProgressService.save(loggedUser.getId(), book.getId(), BookStateEnum.WISHLIST);
        return "redirect:/bookdetails/" + book.getId();
    }

    @PostMapping("/add-to-currently-reading")
    public String addToCurrentlyReading(@ModelAttribute("book") Book book) {
        com.project.bookstore.model.User loggedUser = getLoggedUser();
        bookProgressService.save(loggedUser.getId(), book.getId(), BookStateEnum.CURRENTLY_READING);
        return "redirect:/bookdetails/" + book.getId();
    }

    @PostMapping("/add-to-read")
    public String addToRead(@ModelAttribute("book") Book book) {
        com.project.bookstore.model.User loggedUser = getLoggedUser();
        bookProgressService.save(loggedUser.getId(), book.getId(), BookStateEnum.READ);
        return "redirect:/bookdetails/" + book.getId();
    }



    private com.project.bookstore.model.User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }
}
