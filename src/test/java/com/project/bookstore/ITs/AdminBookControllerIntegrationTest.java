package com.project.bookstore.ITs;

import com.project.bookstore.model.Author;
import com.project.bookstore.model.Book;
import com.project.bookstore.model.Genre;
import com.project.bookstore.repository.AuthorRepository;
import com.project.bookstore.repository.GenreRepository;
import com.project.bookstore.rest.mvc.AdminBookController;
import com.project.bookstore.rest.mvc.ModelAttributesController;
import com.project.bookstore.security.PasswordEncoderConfiguration;
import com.project.bookstore.security.UserSecurityService;
import com.project.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer test for AdminBookController's POST routes: authorization enforcement
 * (the same /admin/** matcher + real filter chain approach as
 * BookControllerAuthorizationIntegrationTest) and the authorIds/genreIds-to-entities
 * resolution logic, with BookService mocked.
 */
@WebMvcTest(controllers = AdminBookController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ModelAttributesController.class))
@Import(PasswordEncoderConfiguration.class)
class AdminBookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    // SecurityConfiguration is picked up by @WebMvcTest and needs these collaborators.
    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void createBook_returns403_whenUserIsNotAdmin() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "Some Book"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createBook_resolvesAuthorAndGenreIdsIntoEntities_whenUserIsAdmin() throws Exception {
        final var author = new Author();
        author.setId(3L);
        final var genre = new Genre();
        genre.setId(7L);
        when(authorRepository.findAllById(List.of(3L))).thenReturn(List.of(author));
        when(genreRepository.findAllById(List.of(7L))).thenReturn(List.of(genre));

        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "Some Book")
                        .param("description", "A description")
                        .param("authorIds", "3")
                        .param("genreIds", "7"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).saveBook(captor.capture());
        final var savedBook = captor.getValue();
        assertThat(savedBook.getBookTitle()).isEqualTo("Some Book");
        assertThat(savedBook.getDescription()).isEqualTo("A description");
        assertThat(savedBook.getAuthorInBooks()).containsExactly(author);
        assertThat(savedBook.getGenresInBooks()).containsExactly(genre);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createBook_setsEmptyAuthorsAndGenres_whenNoneSelected() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "No Relations Book"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).saveBook(captor.capture());
        assertThat(captor.getValue().getAuthorInBooks()).isEmpty();
        assertThat(captor.getValue().getGenresInBooks()).isEmpty();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateBook_delegatesToServiceWithResolvedRelations() throws Exception {
        final var author = new Author();
        author.setId(5L);
        when(authorRepository.findAllById(List.of(5L))).thenReturn(List.of(author));
        when(genreRepository.findAllById(List.of())).thenReturn(List.of());

        mockMvc.perform(post("/admin/books/42")
                        .with(csrf())
                        .param("bookTitle", "Updated Title")
                        .param("authorIds", "5"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).updateBook(captor.capture(), eq(42L));
        assertThat(captor.getValue().getBookTitle()).isEqualTo("Updated Title");
        assertThat(captor.getValue().getAuthorInBooks()).containsExactly(author);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void deleteBook_returns403_whenUserIsNotAdmin() throws Exception {
        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(post("/admin/books/42/delete").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteBook_delegatesToService_whenUserIsAdmin() throws Exception {
        doNothing().when(bookService).deleteBookById(42L);

        mockMvc.perform(post("/admin/books/42/delete").with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(bookService).deleteBookById(42L);
    }
}
