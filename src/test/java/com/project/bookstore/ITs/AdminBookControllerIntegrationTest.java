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
import com.project.bookstore.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Web-layer test for AdminBookController's POST routes: authorization enforcement
 * (the same /admin/** matcher + real filter chain approach as
 * BookControllerAuthorizationIntegrationTest) and the authorNames/genreNames
 * find-or-create-by-name resolution logic, with BookService mocked.
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

    @MockBean
    private CloudinaryService cloudinaryService;

    // SecurityConfiguration is picked up by @WebMvcTest and needs these collaborators.
    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void dashboard_returns403_whenUserIsNotAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void dashboard_redirectsToLogin_whenAnonymous() throws Exception {
        // Unauthenticated (vs. authenticated-but-wrong-role above) hits formLogin()'s
        // entry point, which redirects to the login page rather than a 403.
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void dashboard_showsPaginatedBooksAndStats_whenUserIsAdmin() throws Exception {
        final var book = new Book();
        book.setId(1L);
        book.setBookTitle("Some Book");
        final Page<Book> page = new PageImpl<>(List.of(book), PageRequest.of(0, 10), 1);
        when(bookService.findAll(any(Pageable.class))).thenReturn(page);
        when(authorRepository.count()).thenReturn(5L);
        when(genreRepository.count()).thenReturn(3L);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attribute("totalBooks", 1L))
                .andExpect(model().attribute("totalAuthors", 5L))
                .andExpect(model().attribute("totalGenres", 3L));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void dashboard_sortsByNewestFirst_byDefault() throws Exception {
        final Page<Book> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(bookService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("sort", "newest"));

        final var captor = forClass(Pageable.class);
        verify(bookService).findAll(captor.capture());
        final var order = captor.getValue().getSort().getOrderFor("id");
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void dashboard_sortsByOldestFirst_whenSortParamIsOldest() throws Exception {
        final Page<Book> page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        when(bookService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/admin").param("sort", "oldest"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("sort", "oldest"));

        final var captor = forClass(Pageable.class);
        verify(bookService).findAll(captor.capture());
        final var order = captor.getValue().getSort().getOrderFor("id");
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

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
    void createBook_reusesExistingAuthorAndGenre_whenNameAlreadyExists() throws Exception {
        final var author = new Author();
        author.setId(3L);
        author.setName("Dan Brown");
        final var genre = new Genre();
        genre.setId(7L);
        genre.setType("Mystery");
        when(authorRepository.findByName("Dan Brown")).thenReturn(author);
        when(genreRepository.findByType("Mystery")).thenReturn(genre);

        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "Some Book")
                        .param("description", "A description")
                        .param("authorNames", "Dan Brown")
                        .param("genreNames", "Mystery"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).saveBook(captor.capture());
        final var savedBook = captor.getValue();
        assertThat(savedBook.getBookTitle()).isEqualTo("Some Book");
        assertThat(savedBook.getDescription()).isEqualTo("A description");
        assertThat(savedBook.getAuthorInBooks()).containsExactly(author);
        assertThat(savedBook.getGenresInBooks()).containsExactly(genre);
        verify(authorRepository, never()).save(any(Author.class));
        verify(genreRepository, never()).save(any(Genre.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createBook_createsNewAuthorAndGenre_whenNameDoesNotExistYet() throws Exception {
        when(authorRepository.findByName("New Author")).thenReturn(null);
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(genreRepository.findByType("New Genre")).thenReturn(null);
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "Some Book")
                        .param("authorNames", "New Author")
                        .param("genreNames", "New Genre"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).saveBook(captor.capture());
        assertThat(captor.getValue().getAuthorInBooks())
                .extracting(Author::getName).containsExactly("New Author");
        assertThat(captor.getValue().getGenresInBooks())
                .extracting(Genre::getType).containsExactly("New Genre");
        verify(authorRepository).save(any(Author.class));
        verify(genreRepository).save(any(Genre.class));
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
    void createBook_addsSuccessFlashMessage_withBookTitle() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", "Some Book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMessage", "\"Some Book\" was added successfully."));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createBook_redisplaysFormWithError_whenTitleIsBlank() throws Exception {
        mockMvc.perform(post("/admin/books")
                        .with(csrf())
                        .param("bookTitle", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book-form"))
                .andExpect(model().attributeHasFieldErrors("book", "bookTitle"));

        verify(bookService, never()).saveBook(any(Book.class));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateBook_delegatesToServiceWithResolvedRelations() throws Exception {
        final var author = new Author();
        author.setId(5L);
        author.setName("Veronica Roth");
        when(authorRepository.findByName("Veronica Roth")).thenReturn(author);

        mockMvc.perform(post("/admin/books/42")
                        .with(csrf())
                        .param("bookTitle", "Updated Title")
                        .param("authorNames", "Veronica Roth"))
                .andExpect(status().is3xxRedirection());

        final var captor = forClass(Book.class);
        verify(bookService).updateBook(captor.capture(), eq(42L));
        assertThat(captor.getValue().getBookTitle()).isEqualTo("Updated Title");
        assertThat(captor.getValue().getAuthorInBooks()).containsExactly(author);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateBook_redisplaysFormWithError_whenTitleIsBlank() throws Exception {
        mockMvc.perform(post("/admin/books/42")
                        .with(csrf())
                        .param("bookTitle", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/book-form"))
                .andExpect(model().attributeHasFieldErrors("book", "bookTitle"));

        verify(bookService, never()).updateBook(any(Book.class), eq(42L));
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
