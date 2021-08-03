package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.WishList;
import com.project.bookstore.model.WishListKey;
import com.project.bookstore.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService{

    @Autowired
    WishListRepository wishListRepository;


    @Override
    public List<WishList> findAll() {
        return wishListRepository.findAll();
    }

    public List<Book> wishListBooksByUserEmail(String email){
        List<Book> wishListBooks = new ArrayList<>();
        List<WishList> wishLists = wishListRepository.findAllByUserEmail(email);
        for(WishList wishList:wishLists){
            wishListBooks.add(wishList.getBook());
        }
        return wishListBooks;
    }

    @Override
    public WishList save(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    public void deleteBookById(WishListKey wishListKey){
        wishListRepository.deleteById(wishListKey);
    }
}
