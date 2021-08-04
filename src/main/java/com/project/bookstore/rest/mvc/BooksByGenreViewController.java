package com.project.bookstore.rest.mvc;

import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BooksByGenreViewController {

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/books-by-genre/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if (optionalGenre.isPresent()){
            Genre genre = optionalGenre.get();
            model.addAttribute("books", genre.getBooksForGenre());
            return "books-by-genre";
        }
        else{
            return "redirect:/";
        }
    }
}