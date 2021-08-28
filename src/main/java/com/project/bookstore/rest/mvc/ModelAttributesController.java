package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

//This controller is used for giving to the thymeleaf model the authentificated user first name when requested

@ControllerAdvice
public class ModelAttributesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenreRepository genreRepository;


    @ModelAttribute("currentUserName")
    //when html page asks for "currentUserName" model attribute, he gets the value from the return of this function
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        if (loggedInEmail.equals("anonymousUser")) //this is value for not logged in user => no user is logged in so we return an empty string of the user name
            return null;

        return userRepository.findByEmail(loggedInEmail).getName();
    }

    @ModelAttribute("loggedUserId")
    public Long getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        if (loggedInEmail.equals("anonymousUser")) //this is value for not logged in user
            return 0L;
        return userRepository.findByEmail(loggedInEmail).getId();
    }

    @ModelAttribute("usedGenres")
    public Set<Genre> getAllGenres() {
        return genreRepository.getAllUsedGenres();
    }

    @ModelAttribute("isAdmin")
    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        return false;
    }
}
