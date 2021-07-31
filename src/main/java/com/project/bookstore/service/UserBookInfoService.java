package com.project.bookstore.service;


import com.project.bookstore.model.UserBookInfo;

import java.util.List;

public interface UserBookInfoService {

    List<UserBookInfo> findAll();
    //save precondition exc rating si pages (sa se incadreze intre nr de pagini la books)
}
