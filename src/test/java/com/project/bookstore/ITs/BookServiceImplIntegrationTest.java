package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Real-database counterpart to BookServiceImplUnitTest: exercises
 * BookService.findSuggestions() and BookService.listAll() against the seeded
 * Testcontainers MySQL instance, so the native queries in BookRepository actually run
 * (a mocked repository in a unit test can't catch a broken native query).
 */
public class BookServiceImplIntegrationTest extends BaseTest {

    @Autowired
    private BookService bookService;

    @Test
    void findSuggestions_returnsBooksMatchingTitle_whenKeywordMatchesABookTitle() {
        final var suggestions = bookService.findSuggestions("Origin");

        assertThat(suggestions).extracting("bookTitle").contains("Origin");
    }

    @Test
    void findSuggestions_returnsBooksMatchingAuthor_whenKeywordMatchesAnAuthorName() {
        final var suggestions = bookService.findSuggestions("Frankopan");

        assertThat(suggestions).extracting("bookTitle").contains("The Silk Roads");
    }

    @Test
    void findSuggestions_ignoresStopwords_insteadOfMatchingEveryBookThatContainsThem() {
        final var suggestions = bookService.findSuggestions("the origin");

        // "The Silk Roads" contains "the" too - without stopword filtering it would
        // show up here just as noise, unrelated to what was actually searched for.
        assertThat(suggestions).extracting("bookTitle").containsExactly("Origin");
    }

    @Test
    void findSuggestions_returnsEmptySet_whenKeywordIsBlank() {
        final var suggestions = bookService.findSuggestions("   ");

        assertThat(suggestions).isEmpty();
    }

    @Test
    void listAll_returnsBooksMatchingTitle_whenKeywordMatchesABookTitle() {
        final var results = bookService.listAll("Origin");

        assertThat(results).extracting("bookTitle").contains("Origin");
    }

    @Test
    void listAll_returnsBooksMatchingAuthor_whenKeywordMatchesAnAuthorName() {
        final var results = bookService.listAll("Frankopan");

        assertThat(results).extracting("bookTitle").contains("The Silk Roads");
    }

    @Test
    void listAll_ignoresStopwords_insteadOfMatchingEveryBookThatContainsThem() {
        final var results = bookService.listAll("the origin");

        // "The Silk Roads" contains "the" too - without stopword filtering it would
        // show up here just as noise, unrelated to what was actually searched for.
        assertThat(results).extracting("bookTitle").containsExactly("Origin");
    }

    @Test
    void listAll_returnsEmptySet_whenKeywordIsBlank() {
        final var results = bookService.listAll("   ");

        assertThat(results).isEmpty();
    }
}
