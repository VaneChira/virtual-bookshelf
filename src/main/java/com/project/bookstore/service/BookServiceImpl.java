package com.project.bookstore.service;


import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Book> result = bookRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        }
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);

    }

    @Override
    public void deleteBookById(Long id) {
        Optional<Book> result = bookRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        }
        else {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public Book updateBook(Book book, Long id) {
        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            Book updatedBook = book;
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        }
        else {
            throw new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName());
        }
    }
}
