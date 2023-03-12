package com.project.bookstore.service;

import com.project.bookstore.exception.PreconditionFailedException;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.BookProgress;
import com.project.bookstore.model.BookProgressKey;
import com.project.bookstore.model.BookStateEnum;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        final var progresses = bookProgressRepository.findAllByUserEmail(email);
        final var bookList = new ArrayList<Book>();
        for (BookProgress bookProgress : progresses) {
            bookList.add(bookProgress.getBook());
        }
        return bookList;
    }

    @Override
    public BookProgress save(Long userId, Long bookId, BookStateEnum bookStateEnum) {
        final var optUser = userRepository.findById(userId);
        final var optBook = bookRepository.findById(bookId);
        if (optUser.isPresent() && optBook.isPresent()) {
            final var bookProgress = new BookProgress(new BookProgressKey(userId, bookId), optUser.get(),
                    optBook.get(), BookStateEnum.fromEnumToInt(bookStateEnum));
            return bookProgressRepository.save(bookProgress);
        } else {
            throw new PreconditionFailedException("User or book id invalid", "");
        }
    }

    @Override
    public BookProgress updatePages(Long userId, Long bookId, Long pages) {
        final var optUser = userRepository.findById(userId);
        final var optBook = bookRepository.findById(bookId);
        if (optUser.isPresent() && optBook.isPresent()) {
            if (pages < 0 || pages > optBook.get().getPages()) {
                throw new PreconditionFailedException("Number of pages not in range for this book", "Pages error");
            }
            final var bookPagesProgress = new BookProgress(new BookProgressKey(userId, bookId), optUser.get(),
                    optBook.get(), pages, BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));
            return bookProgressRepository.save(bookPagesProgress);
        }
        return null;
    }
}
