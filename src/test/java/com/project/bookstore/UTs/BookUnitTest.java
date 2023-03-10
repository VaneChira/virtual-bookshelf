package com.project.bookstore.UTs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookUnitTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void when_insertNewBook_then_countIncreases() {
        final var countAll = bookRepository.findAll().size();
        final var addedBook = bookRepository.save(new Book());
        final var countAfterAdd = bookRepository.findAll().size();
        assert (countAll + 1 == countAfterAdd);

        bookRepository.deleteById(addedBook.getId());
        final var countAfterDelete = bookRepository.findAll().size();
        assert (countAll == countAfterDelete);
    }

    @Test
    void when_updateBookTitle_then_accessIt() {
        final var book = bookRepository.findById(BOOK_ID).get();
        final var originalTitle = book.getBookTitle();
        book.setBookTitle("new Title");
        final var updatedBook = bookRepository.save(book);
        assert (!updatedBook.getBookTitle().equals(originalTitle));

        //revert changes
        updatedBook.setBookTitle("Origin");
        bookRepository.save(updatedBook);
    }
}
