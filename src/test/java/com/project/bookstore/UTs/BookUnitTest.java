package com.project.bookstore.UTs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookUnitTest extends BaseTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void when_insertNewBook_then_countIncreases() {
        Book testBook = new Book();
        int countAll = bookRepository.findAll().size();
        Book addedBook = bookRepository.save(testBook);

        int countAfterAdd = bookRepository.findAll().size();
        assert (countAll + 1 == countAfterAdd);

        bookRepository.deleteById(addedBook.getId());
        int countAfterDelete = bookRepository.findAll().size();
        assert (countAll == countAfterDelete);
    }


    @Test
    void when_updateBookTitle_then_accessIt() {
        Book book = bookRepository.findById(BOOK_ID).get();
        String originalTitle = book.getBookTitle();
        book.setBookTitle("new Title");
        Book updatedBook = bookRepository.save(book);
        assert (!updatedBook.getBookTitle().equals(originalTitle));

        //revert changes
        updatedBook.setBookTitle("Origin");
        bookRepository.save(updatedBook);
    }

}
