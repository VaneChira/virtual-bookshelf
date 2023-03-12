package com.project.bookstore.service;

import com.project.bookstore.model.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
     List<Book> findAll();

     Book findBookById(Long id);

     void saveBook(Book book);

     void deleteBookById(Long id);

     Book updateBook(Book book, Long id);

    Set<Book> listAll(String keyword);

    Set<Book> getStatelessBooksByUserId(Long userId);
}
