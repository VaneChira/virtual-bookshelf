package com.project.bookstore.UTs;

import com.project.bookstore.rest.controller.BookController;
import com.project.bookstore.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Pure unit test for {@link BookController#uploadCoverImage}: only the try/catch
 * translation of the service result into an HTTP response is exercised.
 */
@ExtendWith(MockitoExtension.class)
class BookControllerUploadCoverUnitTest {

    @Mock
    private CloudinaryService cloudinaryService;

    private BookController bookController;

    private final MultipartFile file =
            new MockMultipartFile("file", "cover.jpg", "image/jpeg", "bytes".getBytes());

    @BeforeEach
    void setUp() {
        bookController = new BookController(cloudinaryService);
    }

    @Test
    void uploadCoverImage_returnsOkWithSecureUrl_whenUploadSucceeds() throws IOException {
        when(cloudinaryService.uploadImage(any())).thenReturn("https://res.cloudinary.com/demo/cover.jpg");

        final ResponseEntity<String> response = bookController.uploadCoverImage(file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("https://res.cloudinary.com/demo/cover.jpg");
    }

    @Test
    void uploadCoverImage_returns500WithMessage_whenUploadThrowsIOException() throws IOException {
        when(cloudinaryService.uploadImage(any())).thenThrow(new IOException("boom"));

        final ResponseEntity<String> response = bookController.uploadCoverImage(file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Failed to upload image.");
    }
}
