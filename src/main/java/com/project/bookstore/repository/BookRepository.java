package com.project.bookstore.repository;

import com.project.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository <Book, Integer>{
    //This is JPA Repository for books.
}
