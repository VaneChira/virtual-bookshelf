package com.project.bookstore.UTs;

import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pure unit test for {@link BookServiceImpl}: BookRepository is fully mocked, no
 * Spring context and no database.
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplUnitTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void listAll_returnsBooksMatchingTitle_whenKeywordMatchesABookTitle() {
        final var book = new Book();
        book.setId(1L);
        book.setBookTitle("The Great Gatsby");
        when(bookRepository.search("Gatsby")).thenReturn(Set.of(book));

        final var result = bookService.listAll("Gatsby");

        assertThat(result).containsExactly(book);
        verify(bookRepository).search("Gatsby");
    }

    @Test
    void listAll_returnsBooksMatchingAuthor_whenKeywordMatchesAnAuthorName() {
        final var author = new Author();
        author.setId(2L);
        author.setName("Agatha Christie");
        final var book = new Book();
        book.setId(3L);
        book.setBookTitle("Murder on the Orient Express");
        book.setAuthorInBooks(Set.of(author));
        when(bookRepository.search("Agatha")).thenReturn(Set.of(book));

        final var result = bookService.listAll("Agatha");

        assertThat(result).containsExactly(book);
        verify(bookRepository).search("Agatha");
    }

    @Test
    void listAll_ignoresStopwords_whenKeywordHasMultipleWords() {
        final var book = new Book();
        book.setId(7L);
        book.setBookTitle("Origin");
        when(bookRepository.search("origin")).thenReturn(Set.of(book));

        final var result = bookService.listAll("the origin");

        assertThat(result).containsExactly(book);
        verify(bookRepository).search("origin");
        verify(bookRepository, never()).search("the");
    }

    @Test
    void listAll_searchesWholeKeyword_whenEveryWordIsAStopword() {
        when(bookRepository.search("the")).thenReturn(Set.of());

        final var result = bookService.listAll("the");

        assertThat(result).isEmpty();
        verify(bookRepository).search("the");
    }

    @Test
    void listAll_returnsEmptySet_whenKeywordIsBlank_withoutQueryingRepository() {
        final var result = bookService.listAll("   ");

        assertThat(result).isEmpty();
        verify(bookRepository, never()).search(any());
        verify(bookRepository, never()).findAll();
    }

    @Test
    void listAll_returnsEmptySet_whenKeywordIsNull_withoutQueryingRepository() {
        final var result = bookService.listAll(null);

        assertThat(result).isEmpty();
        verify(bookRepository, never()).search(any());
        verify(bookRepository, never()).findAll();
    }

    @Test
    void findSuggestions_returnsMatchingBooks_whenKeywordProvided() {
        final var book = new Book();
        book.setId(5L);
        book.setBookTitle("The Hobbit");
        when(bookRepository.findSuggestions("Hobbit")).thenReturn(Set.of(book));

        final var result = bookService.findSuggestions("Hobbit");

        assertThat(result).containsExactly(book);
        verify(bookRepository).findSuggestions("Hobbit");
    }

    @Test
    void findSuggestions_ignoresStopwords_whenKeywordHasMultipleWords() {
        final var book = new Book();
        book.setId(6L);
        book.setBookTitle("Origin");
        when(bookRepository.findSuggestions("origin")).thenReturn(Set.of(book));

        final var result = bookService.findSuggestions("the origin");

        assertThat(result).containsExactly(book);
        verify(bookRepository).findSuggestions("origin");
        verify(bookRepository, never()).findSuggestions("the");
    }

    @Test
    void findSuggestions_capsResultsAtFive_whenMoreThanFiveMatchesFound() {
        final var books = IntStream.rangeClosed(1, 7)
                .mapToObj(i -> {
                    final var book = new Book();
                    book.setId((long) i);
                    book.setBookTitle("Book " + i);
                    return book;
                })
                .collect(Collectors.toSet());
        when(bookRepository.findSuggestions("book")).thenReturn(books);

        final var result = bookService.findSuggestions("book");

        assertThat(result).hasSize(5);
    }

    @Test
    void findSuggestions_returnsEmptySet_whenKeywordIsBlank_withoutQueryingRepository() {
        final var result = bookService.findSuggestions("   ");

        assertThat(result).isEmpty();
        verify(bookRepository, never()).findSuggestions(any());
    }

    @Test
    void findSuggestions_returnsEmptySet_whenKeywordIsNull_withoutQueryingRepository() {
        final var result = bookService.findSuggestions(null);

        assertThat(result).isEmpty();
        verify(bookRepository, never()).findSuggestions(any());
    }

}
