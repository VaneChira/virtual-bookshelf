package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.WishList;
import com.project.bookstore.model.WishListKey;

import java.util.List;

public interface WishListService {

    List<WishList> findAll();

    List<Book> wishListBooksByUserEmail(String email);

    WishList save(WishList wishList);

    void deleteBookById(WishListKey wishListKey);



}
