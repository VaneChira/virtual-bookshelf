package com.project.bookstore.ITs;

import com.project.bookstore.rest.controller.BookController;
import com.project.bookstore.rest.mvc.ModelAttributesController;
import com.project.bookstore.security.UserSecurityService;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer integration test for {@code POST /api/books/upload-cover}: a real
 * {@link MockMvc} pipeline (multipart parsing, argument resolution, status mapping,
 * response writing) driving {@link BookController}, with the service collaborators
 * mocked. Security filters are disabled so the test focuses on the endpoint itself.
 */
@WebMvcTest(controllers = BookController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ModelAttributesController.class))
@AutoConfigureMockMvc(addFilters = false)
class CloudinaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private BookService bookService;

    // SecurityConfiguration is picked up by @WebMvcTest and needs this collaborator.
    @MockBean
    private UserSecurityService userSecurityService;

    @Test
    void uploadCover_returns200AndTheSecureUrl() throws Exception {
        when(cloudinaryService.uploadImage(any()))
                .thenReturn("https://res.cloudinary.com/demo/image/upload/v1/book_covers/cover.jpg");

        final var file = new MockMultipartFile(
                "file", "cover.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-bytes".getBytes());

        mockMvc.perform(multipart("/api/books/upload-cover").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "https://res.cloudinary.com/demo/image/upload/v1/book_covers/cover.jpg"));
    }

    @Test
    void uploadCover_returns500AndMessage_whenServiceFails() throws Exception {
        when(cloudinaryService.uploadImage(any())).thenThrow(new IOException("cloudinary unavailable"));

        final var file = new MockMultipartFile(
                "file", "cover.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-bytes".getBytes());

        mockMvc.perform(multipart("/api/books/upload-cover").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to upload image."));
    }

    @Test
    void uploadCover_returns400_whenFilePartIsMissing() throws Exception {
        mockMvc.perform(multipart("/api/books/upload-cover"))
                .andExpect(status().isBadRequest());
    }
}
