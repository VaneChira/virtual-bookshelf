package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.*;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

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
        final var genres = new HashSet<Genre>();
        genres.add(genreRepository.findById(HISTORY_GENRE_ID).get());

        final var book = new Book();
        book.setGenresInBooks(genres);
        final var addedBook = bookRepository.save(book);

        final var postBookInsert = genreRepository.findById(HISTORY_GENRE_ID).get();

        Hibernate.initialize(postBookInsert.getBooksForGenre());

        final var booksForGivenGenre = postBookInsert.getBooksForGenre();
        boolean foundBook = false;

        for (Book b : booksForGivenGenre) {
            if (b.getId().equals(addedBook.getId())) {
                foundBook = true;
                break;
            }
        }
        bookRepository.deleteById(addedBook.getId());
        assert (foundBook);
    }


    @Test
    void when_userUpdatesFeedback_then_feedbackGetsUpdated() {
        final var feedback = new Feedback(
                new FeedbackKey(USER_ID, BOOK_ID),
                userRepository.findById(USER_ID).get(),
                bookRepository.findById(BOOK_ID).get(),
                RATING,
                COMMENT,
                LOCAL_DATE);

        final var preUpdateFeedback = feedbackRepository.save(feedback);

        final var preUpdateFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        preUpdateFeedback.setComment("Updated comment");
        final var savedFeedback = feedbackRepository.save(preUpdateFeedback);

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
        final var feedback = new Feedback(
                new FeedbackKey(USER_ID, BOOK_ID),
                userRepository.findById(USER_ID).get(),
                bookRepository.findById(BOOK_ID).get(),
                RATING,
                COMMENT,
                LOCAL_DATE);

        final var preFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
        final var savedFeedback = feedbackRepository.save(feedback);

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
    void when_addingBookToCurrentlyReading_then_statusChanges() {
        final var user = userRepository.findById(USER_ID).get();
        final var book = bookRepository.findById(BOOK_ID).get();

        final var bookProgress = BookProgress.builder()
                .bookProgressKey(new BookProgressKey(USER_ID, BOOK_ID))
                .book(book)
                .user(user)
                .progressPage(PROGRESS_PAGE)
                .bookState(BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING))
                .build();

        final var preInsertionCount = bookProgressRepository.count();
        final var savedBookProgress = bookProgressRepository.save(bookProgress);

        final var postInsertionCount = bookProgressRepository.count();
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
        final var user = userRepository.findById(USER_ID).get();
        final var book = bookRepository.findById(BOOK_ID).get();

        final var bookProgress = BookProgress.builder()
                .bookProgressKey(new BookProgressKey(USER_ID, BOOK_ID))
                .book(book)
                .user(user)
                .bookState(BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST))
                .build();

        final var preInsertionCount = bookProgressRepository.count();
        final var savedBookProgress = bookProgressRepository.save(bookProgress);

        final var postInsertionCount = bookProgressRepository.count();
        try {
            assert (preInsertionCount == postInsertionCount - 1);
            assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));
            assert (savedBookProgress.getBook().getId().equals(book.getId()));
            assert (savedBookProgress.getUser().getId().equals(user.getId()));
        } finally {
            bookProgressRepository.deleteById(savedBookProgress.getBookProgressKey());
        }
    }
}
