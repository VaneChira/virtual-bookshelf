package com.project.bookstore.service;

import com.project.bookstore.exception.PreconditionFailedException;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserBookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserBookInfoServiceImpl implements UserBookInfoService {

    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<UserBookInfo> findAll() {
        return userBookRepository.findAll();
    }

    @Override
    public List<Book> findAllBooksByUserEmail(String email) {
        List<UserBookInfo> userBooks = userBookRepository.findAllByUserEmail(email);
        List<Book> bookList = new ArrayList<>();
        for (UserBookInfo userBookInfo : userBooks) {
            bookList.add(userBookInfo.getBook());
        }
        return bookList;
    }

    @Override
    public UserBookInfo save(Long userId, Long bookId, BookStateEnum bookStateEnum) {
        UserBookKey userBookKey = new UserBookKey(userId, bookId);
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optUser.isPresent() && optBook.isPresent()) {
            UserBookInfo userBookInfo = new UserBookInfo(userBookKey, optUser.get(),
                    optBook.get(), BookStateEnum.fromEnumToInt(bookStateEnum));
            return userBookRepository.save(userBookInfo);

        } else {
            throw new PreconditionFailedException("User or book id invalid", "");
        }
    }
}
