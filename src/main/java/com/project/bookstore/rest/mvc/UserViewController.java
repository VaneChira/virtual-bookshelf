package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.User;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Long userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent())
            return "redirect:/";
        else {
            User user = userOpt.get();
            model.addAttribute("user", user);
            model.addAttribute("numberOfWishlist", userRepository.getWishlistCountByUserId(userId));
            model.addAttribute("numberOfCurrentlyReading", userRepository.getCurrentlyReadingCountByUserId(userId));
            model.addAttribute("numberOfRead", userRepository.getReadCountByUserId(userId));
            model.addAttribute("mostReadGenres", userService.getMostReadGenres(userId).entrySet()
                    .stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));

            model.addAttribute("readBooks", bookRepository.findAllReadByUser(userId));
            model.addAttribute("currentlyReadingBooks", bookRepository.findAllCurrentlyReadingByUser(userId));
        }
        return "userprofile";
    }

}
