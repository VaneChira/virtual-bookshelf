package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.*;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class BookIntegrationTest extends BaseTest {

    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    GenreRepository genreRepository;


    @Test
    void findById_givenExistingGenre_returnBooksByGenre() {
        Book book = new Book();

        Genre testGenre = genreRepository.findById(HISTORY_GENRE_ID).get();
        Set<Genre> genres = new HashSet<>();
        genres.add(testGenre);

        book.setGenresInBooks(genres);
        Book addedBook = bookRepository.save(book);

        Genre postBookInsert = genreRepository.findById(HISTORY_GENRE_ID).get();

        Hibernate.initialize(postBookInsert.getBooksForGenre()); // pentru intializare getBooksForGenre, alfel linia urmatoare da eroare (field ne initalizat)

        Set<Book> booksForGivenGenre = postBookInsert.getBooksForGenre();
        boolean foundBook = false;

        for (Book b : booksForGivenGenre) {
            if (b.getId().equals(addedBook.getId())) {
                foundBook = true;
                break;
            }
        }
        bookRepository.deleteById(addedBook.getId()); // cleanup
        assert (foundBook);
    }


    @Test
    void when_userUpdatesFeedback_then_feedbackGetsUpdated() {
        FeedbackKey feedbackKey = new FeedbackKey(USER_ID, BOOK_ID);

        User user = userRepository.findById(USER_ID).get();
        Book book = bookRepository.findById(BOOK_ID).get();

        Feedback feedback = new Feedback(feedbackKey,
                user,
                book,
                RATING,
                COMMENT,
                LOCAL_DATE);

        Feedback preUpdateFeedback = feedbackRepository.save(feedback);

        int preUpdateFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        preUpdateFeedback.setComment("Updated comment");
        Feedback savedFeedback = feedbackRepository.save(preUpdateFeedback);

        int postFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        try {
            assert (preUpdateFeedbackCount == postFeedbackCount);
            assert (savedFeedback.getRating().equals(RATING));
            assert (savedFeedback.getComment().equals("Updated comment"));
            assert (savedFeedback.getDate().equals(LOCAL_DATE));
        } finally {
            feedbackRepository.deleteById(savedFeedback.getFeedbackKey());
        }
    }


    @Test
    void when_givingFeedbackToABookById_then_feedbackGetsCreated() {
        FeedbackKey feedbackKey = new FeedbackKey(USER_ID, BOOK_ID);

        User user = userRepository.findById(USER_ID).get();
        Book book = bookRepository.findById(BOOK_ID).get();

        Feedback feedback = new Feedback(feedbackKey,
                user,
                book,
                RATING,
                COMMENT,
                LOCAL_DATE);

        int preFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        Feedback savedFeedback = feedbackRepository.save(feedback);

        int postFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        try {
            assert (preFeedbackCount == postFeedbackCount - 1);
            assert (savedFeedback.getRating().equals(RATING));
            assert (savedFeedback.getComment().equals(COMMENT));
            assert (savedFeedback.getDate().equals(LOCAL_DATE));
        } finally {
            feedbackRepository.deleteById(savedFeedback.getFeedbackKey());
        }
    }


    @Test
    void when_addingBookToCurrentlyReading_then_statusChanges() { //integration test
        BookProgressKey bookProgressKey = new BookProgressKey(USER_ID, BOOK_ID);

        User user = userRepository.findById(USER_ID).get();
        Book book = bookRepository.findById(BOOK_ID).get();

        BookProgress bookProgress = new BookProgress(bookProgressKey,
                user,
                book,
                PROGRESS_PAGE,
                BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));

        long preInsertionCount = bookProgressRepository.count();
        BookProgress savedBookProgress = bookProgressRepository.save(bookProgress);

        long postInsertionCount = bookProgressRepository.count();
        try {
            assert (preInsertionCount == postInsertionCount - 1);
            assert (savedBookProgress.getProgressPage() == PROGRESS_PAGE);
            assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));
            assert (savedBookProgress.getBook().getId().equals(book.getId()));
            assert (savedBookProgress.getUser().getId().equals(user.getId()));
        } finally {
            bookProgressRepository.deleteById(savedBookProgress.getBookProgressKey());
        }
    }


    @Test
    void when_addingBookToWishlist_then_accessIt() {
        BookProgressKey bookProgressKey = new BookProgressKey(USER_ID, BOOK_ID);

        User user = userRepository.findById(USER_ID).get();
        Book book = bookRepository.findById(BOOK_ID).get();

        BookProgress bookProgress = new BookProgress(bookProgressKey,
                user,
                book,
                PROGRESS_PAGE,
                BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));

        long preInsertionCount = bookProgressRepository.count();
        BookProgress savedBookProgress = bookProgressRepository.save(bookProgress);

        long postInsertionCount = bookProgressRepository.count();
        try {
            assert (preInsertionCount == postInsertionCount - 1);
            assert (savedBookProgress.getProgressPage() == PROGRESS_PAGE);
            assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));
            assert (savedBookProgress.getBook().getId().equals(book.getId()));
            assert (savedBookProgress.getUser().getId().equals(user.getId()));
        } finally {
            bookProgressRepository.deleteById(savedBookProgress.getBookProgressKey());
        }

    }
}
