package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

//This controller is used for giving to the thymeleaf model the authentificated user first name when requested

@ControllerAdvice
public class ModelAttributesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenreRepository genreRepository;


    @ModelAttribute("currentUser") //when html page asks for "currentUser" model attribute, he gets the value from the return of this function
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedinEmail = authentication.getName();
        if (loggedinEmail.equals("anonymousUser")) //this is value for not logged in user => no user is logged in so we return an empty string of the user name
            return null;

        com.project.bookstore.model.User modelUser = userRepository.findByEmail(loggedinEmail);

        return modelUser.getName();
    }


    @ModelAttribute("usedGenres")
    public List<Genre> getAllGenres(){
       return genreRepository.getAllUsedGenres();
    }
}
