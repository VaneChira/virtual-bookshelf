package com.project.bookstore.repository;

import com.project.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long > {
    Author findByName(String name);
}