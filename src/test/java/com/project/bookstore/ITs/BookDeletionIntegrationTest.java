package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.AuthorRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Regression coverage for book deletion, end to end against the real database.
 * <p>
 * The genre/author tests cover deleting a book with relations attached. The genre case
 * was a real, confirmed bug: Genre.booksForGenre was fetch = EAGER, cascade = ALL;
 * ModelAttributesController (a global @ControllerAdvice) eagerly loads every genre's
 * books on every request, and with open-in-view keeping the persistence context alive
 * for the whole request, that cascade interfered with the scheduled delete before it
 * could flush — the endpoint returned 200 OK but silently left the row in place. The
 * author case was verified working correctly at the time (Book.authorInBooks is the
 * owning side of its join table, so Hibernate clears it directly), but gets its own
 * test here too, since it's the same category of relation and deserves the same
 * regression safety net if that mapping ever changes.
 * <p>
 * The remaining two tests cover the admin dashboard's delete route specifically (full
 * stack, not the mocked-service slice test) and confirm the old admin-gated Delete
 * button that used to live on the regular bookdetails page — since replaced by the
 * admin dashboard's own delete — actually stays gone.
 * <p>
 * All tests deliberately go through a real HTTP request (not a direct repository call)
 * so ModelAttributesController actually runs, matching real request conditions.
 */
@AutoConfigureMockMvc
public class BookDeletionIntegrationTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser(username = "anne@example.com", authorities = "ROLE_ADMIN")
    void when_deletingBookWithGenreAttached_then_bookIsActuallyRemoved() throws Exception {
        final var genre = genreRepository.findById(HISTORY_GENRE_ID).get();
        final var book = new Book();
        book.setBookTitle("Deletion Regression Test Book - Genre");
        book.setDescription("A test description.");
        book.setPages(200L);
        book.setYear(2020);
        book.setLanguage("English");
        book.setGenresInBooks(Set.of(genre));
        final var savedBook = bookRepository.save(book);

        try {
            mockMvc.perform(delete("/api/books/deleteBookById/" + savedBook.getId()).with(csrf()))
                    .andExpect(status().isOk());

            assert (bookRepository.findById(savedBook.getId()).isEmpty());
        } finally {
            if (bookRepository.existsById(savedBook.getId())) {
                bookRepository.deleteById(savedBook.getId());
            }
        }
    }

    @Test
    @WithMockUser(username = "anne@example.com", authorities = "ROLE_ADMIN")
    void when_deletingBookWithAuthorAttached_then_bookIsActuallyRemoved() throws Exception {
        final var author = new Author();
        author.setName("Deletion Regression Test Author");
        final var savedAuthor = authorRepository.save(author);

        final var book = new Book();
        book.setBookTitle("Deletion Regression Test Book - Author");
        book.setDescription("A test description.");
        book.setPages(200L);
        book.setYear(2020);
        book.setLanguage("English");
        book.setAuthorInBooks(Set.of(savedAuthor));
        final var savedBook = bookRepository.save(book);

        try {
            mockMvc.perform(delete("/api/books/deleteBookById/" + savedBook.getId()).with(csrf()))
                    .andExpect(status().isOk());

            assert (bookRepository.findById(savedBook.getId()).isEmpty());
        } finally {
            if (bookRepository.existsById(savedBook.getId())) {
                bookRepository.deleteById(savedBook.getId());
            }
            authorRepository.deleteById(savedAuthor.getId());
        }
    }

    /**
     * The admin dashboard's own delete route (POST /admin/books/{id}/delete) already has
     * a controller-level test with BookService mocked (AdminBookControllerIntegrationTest),
     * proving the controller delegates correctly. This one goes further: a real book,
     * saved to the real DB, deleted through that exact route with everything real
     * (controller, service, repository, DB) — proving the admin interface's delete
     * button actually works end to end, not just that it calls the right method.
     */
    @Test
    @WithMockUser(username = "anne@example.com", authorities = "ROLE_ADMIN")
    void when_deletingBookThroughAdminInterface_then_bookIsActuallyRemoved() throws Exception {
        final var genre = genreRepository.findById(HISTORY_GENRE_ID).get();
        final var book = new Book();
        book.setBookTitle("Admin Interface Deletion Test Book");
        book.setDescription("A test description.");
        book.setPages(200L);
        book.setYear(2020);
        book.setLanguage("English");
        book.setGenresInBooks(Set.of(genre));
        final var savedBook = bookRepository.save(book);

        try {
            mockMvc.perform(post("/admin/books/" + savedBook.getId() + "/delete").with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/admin"));

            assert (bookRepository.findById(savedBook.getId()).isEmpty());
        } finally {
            if (bookRepository.existsById(savedBook.getId())) {
                bookRepository.deleteById(savedBook.getId());
            }
        }
    }

    /**
     * Confirms the leftover admin-gated Delete button that used to live on the regular
     * reader-facing bookdetails page is actually gone, now that the same action lives
     * properly in the admin dashboard — this is what regresses if that markup ever
     * silently comes back.
     */
    @Test
    @WithMockUser(username = "anne@example.com", authorities = "ROLE_ADMIN")
    void when_adminViewsBookDetails_then_noLeftoverDeleteButtonShown() throws Exception {
        mockMvc.perform(get("/bookdetails/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("deleteBookWindow"))));
    }
}
