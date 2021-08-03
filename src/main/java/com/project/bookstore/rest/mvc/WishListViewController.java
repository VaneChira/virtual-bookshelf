package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.WishList;
import com.project.bookstore.model.WishListKey;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishListViewController {

    @Autowired
    WishListService wishListService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/wishlist")
    public String wishlist(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal(); // user from spring security (not model)

        com.project.bookstore.model.User modelUser = userRepository.findByEmail(user.getUsername());

        model.addAttribute("books", wishListService.wishListBooksByUserEmail(modelUser.getEmail()));

        return "wishlist";
    }

    @PostMapping("/wishlist/add-to-wishlist")
    public String saveWishlist(@ModelAttribute Book book){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal(); // user from spring security (not model)

        com.project.bookstore.model.User modelUser = userRepository.findByEmail(user.getUsername());

        WishListKey wishListKey = new WishListKey(modelUser.getId(), book.getId());
        WishList wishList = new WishList(wishListKey, modelUser, book);
        // check if already exists, if so, alert book already exists in wishlist, otherwise .save();
        wishListService.save(wishList);

        return "redirect:/";
    }


}
