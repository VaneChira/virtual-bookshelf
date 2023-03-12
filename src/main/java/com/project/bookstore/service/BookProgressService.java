package com.project.bookstore.service;


import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgress;
import com.project.bookstore.model.BookStateEnum;

import java.util.List;

public interface BookProgressService {
    List<BookProgress> findAll();

    List<Book> findAllBooksByUserEmail(String email);

    BookProgress save(Long userId, Long bookId, BookStateEnum bookStateEnum);

    BookProgress updatePages(Long userId, Long bookId, Long pages);
}
