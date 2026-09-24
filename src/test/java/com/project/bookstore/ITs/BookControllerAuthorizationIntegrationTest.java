package com.project.bookstore.ITs;

import com.project.bookstore.rest.controller.BookController;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies the @PreAuthorize("hasRole('ADMIN')") annotations on BookController's
 * mutating endpoints are actually enforced end-to-end. Unlike
 * CloudinaryControllerIntegrationTest, this leaves the real security filter chain
 * enabled (no addFilters = false) — AccessDeniedException from a failed @PreAuthorize
 * check only gets translated into a proper 403 response by ExceptionTranslationFilter,
 * which is part of that chain, so a non-admin case needs it running to observe a clean
 * 403 rather than an unhandled exception. Mutating requests need a CSRF token as a
 * result, supplied via the csrf() request post-processor.
 */
@WebMvcTest(controllers = BookController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ModelAttributesController.class))
@Import(PasswordEncoderConfiguration.class)
class BookControllerAuthorizationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CloudinaryService cloudinaryService;

    // SecurityConfiguration is picked up by @WebMvcTest and needs these collaborators.
    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void addBook_returns403_whenUserIsNotAdmin() throws Exception {
        mockMvc.perform(post("/api/books/addBook")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookTitle\":\"Some Book\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void addBook_succeeds_whenUserIsAdmin() throws Exception {
        mockMvc.perform(post("/api/books/addBook")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookTitle\":\"Some Book\",\"description\":\"A test description.\"," +
                                "\"pages\":200,\"year\":2020,\"language\":\"English\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void deleteBookById_returns403_whenUserIsNotAdmin() throws Exception {
        doNothing().when(bookService).deleteBookById(anyLong());

        mockMvc.perform(delete("/api/books/deleteBookById/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
