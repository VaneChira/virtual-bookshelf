package com.project.bookstore.service;

import com.project.bookstore.model.Book;

import java.util.List;

public interface BookService {

     List<Book> findAll();

     Book findBookById(Long id);

     void saveBook(Book book);

     void deleteBookById(Long id);

     Book updateBook(Book book, Long id);

     List<Book> listAll(String keyword);



}
