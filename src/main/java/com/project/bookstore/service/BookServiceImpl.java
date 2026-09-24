package com.project.bookstore.service;

import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    // Common filler words that shouldn't be treated as search terms on their own
    private static final Set<String> STOPWORDS = Set.of(
            "a", "an", "the", "of", "and", "or", "in", "on", "at", "to", "for", "with", "by");

    private static final int MAX_SUGGESTIONS = 5;

    @Autowired
    private BookRepository bookRepository;

    private List<String> significantWords(String keyword) {
        final var words = Arrays.stream(keyword.trim().split("\\s+"))
                .filter(word -> !STOPWORDS.contains(word.toLowerCase()))
                .collect(Collectors.toList());
        return words.isEmpty() ? List.of(keyword.trim()) : words;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
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
        if (keyword == null || keyword.isBlank()) {
            return Set.of();
        }
        final var results = new HashSet<Book>();
        for (String word : significantWords(keyword)) {
            results.addAll(bookRepository.search(word));
        }
        return results;
    }

    @Override
    public Set<Book> findSuggestions(String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return Set.of();
        }
        final var suggestions = new HashSet<Book>();
        for (String word : significantWords(keyword)) {
            suggestions.addAll(bookRepository.findSuggestions(word));
        }
        return suggestions.stream().limit(MAX_SUGGESTIONS).collect(Collectors.toSet());
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
        var existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find book id - " + id, Book.class.getSimpleName()));
        existing.setBookTitle(book.getBookTitle());
        existing.setDescription(book.getDescription());
        existing.setPages(book.getPages());
        existing.setYear(book.getYear());
        existing.setLanguage(book.getLanguage());
        existing.setAuthorInBooks(book.getAuthorInBooks());
        existing.setGenresInBooks(book.getGenresInBooks());
        if(book.getImageUrl() != null) {
            existing.setImageUrl(book.getImageUrl());
        }
        return bookRepository.save(existing);
    }
}
