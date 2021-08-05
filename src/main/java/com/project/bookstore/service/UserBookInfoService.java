package com.project.bookstore.service;


import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookStateEnum;
import com.project.bookstore.model.UserBookInfo;

import java.util.List;

public interface UserBookInfoService {

    List<UserBookInfo> findAll();

    //save precondition exc rating si pages (sa se incadreze intre nr de pagini la books)
    List<Book> findAllBooksByUserEmail(String email);

    UserBookInfo save(Long userId, Long bookId, BookStateEnum bookStateEnum);

}
