package com.project.bookstore.service;

import com.project.bookstore.model.Book;

import java.util.List;

public interface BookService {

     List<Book> findAll();

     Book findBookById(int theId);

     void saveBook(Book theBook);

     void deleteBookById(int theId);

     Book updateBook(Book book, int id);

}
