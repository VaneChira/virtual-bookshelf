package com.project.bookstore.service;

import com.project.bookstore.exception.PreconditionFailedException;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookProgressServiceImpl implements BookProgressService {

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookProgress> findAll() {
        return bookProgressRepository.findAll();
    }

    @Override
    public List<Book> findAllBooksByUserEmail(String email) {
        List<BookProgress> progresses = bookProgressRepository.findAllByUserEmail(email);
        List<Book> bookList = new ArrayList<>();
        for (BookProgress bookProgress : progresses) {
            bookList.add(bookProgress.getBook());
        }
        return bookList;
    }

    @Override
    public BookProgress save(Long userId, Long bookId, BookStateEnum bookStateEnum) {
        BookProgressKey bookProgressKey = new BookProgressKey(userId, bookId);
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optUser.isPresent() && optBook.isPresent()) {
            BookProgress bookProgress = new BookProgress(bookProgressKey, optUser.get(),
                    optBook.get(), BookStateEnum.fromEnumToInt(bookStateEnum));
            return bookProgressRepository.save(bookProgress);

        } else {
            throw new PreconditionFailedException("User or book id invalid", "");
        }
    }
}
