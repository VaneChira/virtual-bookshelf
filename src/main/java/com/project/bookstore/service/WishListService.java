package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.WishList;

import java.util.List;

public interface WishListService {

    List<WishList> findAll();

    public List<Book> wishListBooksByUserEmail(String email);
}
