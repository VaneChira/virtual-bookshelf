package com.project.bookstore.service;

import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @Override
    public Book findBookById(Long id) {
        final var result = bookRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        }
    }

    @Override
    public Set<Book> listAll(String keyword) {
        if (keyword != null) {
            return bookRepository.search(keyword);
        }
        return new HashSet<>(bookRepository.findAll());
    }

    @Override
    public Set<Book> getStatelessBooksByUserId(Long userId) {
        final var allBooks = bookRepository.findAll();
        allBooks.removeAll(bookRepository.findAllStatedBooksByUser(userId));
        return new HashSet<>(allBooks);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);

    }

    @Override
    public void deleteBookById(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        } else {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public Book updateBook(Book book, Long id) {
        if (bookRepository.findById(id).isPresent()) {
            book.setId(id);
            return bookRepository.save(book);
        } else {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        }
    }
}
