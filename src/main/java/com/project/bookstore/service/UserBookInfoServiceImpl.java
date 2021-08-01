package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.model.UserBookInfo;
import com.project.bookstore.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookInfoServiceImpl implements UserBookInfoService {

    @Autowired
    UserBookRepository userBookRepository;

    @Override
    public List<UserBookInfo> findAll() {
        return userBookRepository.findAll();
    }

    @Override
    public List<Book> findAllBooksByUserEmail(String email) {
        List<UserBookInfo> userBooks = userBookRepository.findAllByUserEmail(email);
        List<Book> bookList = new ArrayList<>();
        for(UserBookInfo userBookInfo: userBooks){
            bookList.add(userBookInfo.getBook());
        }
        return bookList;
    }
}
