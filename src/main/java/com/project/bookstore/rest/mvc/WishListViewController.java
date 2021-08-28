package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgressKey;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WishListViewController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        Long userId = getUser().getId();
        List<Book> wishlistBooks = bookRepository.findAllWishlistByUser(userId);
        model.addAttribute("books", wishlistBooks);

        return "wishlist";
    }


    @PostMapping("/wishlist/delete")
    public String delete(@ModelAttribute Book book) {
        Long userId = getUser().getId();
        BookProgressKey bookProgressKey = new BookProgressKey(userId, book.getId());
        bookProgressRepository.deleteById(bookProgressKey);

        return "redirect:/wishlist";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)
        return userRepository.findByEmail(user.getUsername());
    }


}

