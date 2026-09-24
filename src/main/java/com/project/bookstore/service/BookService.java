package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface BookService {
     List<Book> findAll();

     Page<Book> findAll(Pageable pageable);

     Book findBookById(Long id);

     void saveBook(Book book);

     void deleteBookById(Long id);

     Book updateBook(Book book, Long id);

    Set<Book> listAll(String keyword);

    Set<Book> findSuggestions(String keyword);

    Set<Book> getStatelessBooksByUserId(Long userId);
}
