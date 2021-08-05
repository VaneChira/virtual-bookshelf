package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.UserBookInfo;
import com.project.bookstore.model.UserBookKey;
import com.project.bookstore.repository.UserBookRepository;
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
import java.util.stream.Collectors;

@Controller
public class WishListViewController {

    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        com.project.bookstore.model.User modelUser = getUser();
        List<UserBookInfo> userBooks = userBookRepository.findAllWishlistByUser(modelUser.getId());
        model.addAttribute("books", userBooks.stream().map(UserBookInfo::getBook).collect(Collectors.toList()));

        return "wishlist";
    }


    @PostMapping("/wishlist/delete")
    public String delete(@ModelAttribute Book book) {
        com.project.bookstore.model.User modelUser = getUser();

        UserBookKey userBookKey = new UserBookKey(modelUser.getId(), book.getId());
        userBookRepository.deleteById(userBookKey);

        return "redirect:/wishlist";
    }

    private com.project.bookstore.model.User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // user from spring security (not model)

        return userRepository.findByEmail(user.getUsername());
    }


}

