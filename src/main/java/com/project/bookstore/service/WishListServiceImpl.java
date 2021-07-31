package com.project.bookstore.service;

import com.project.bookstore.model.WishList;
import com.project.bookstore.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService{

    @Autowired
    WishListRepository wishListRepository;

    @Override
    public List<WishList> findAll() {
        return wishListRepository.findAll();
    }
}
