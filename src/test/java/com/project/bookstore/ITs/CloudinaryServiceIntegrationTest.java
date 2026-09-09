package com.project.bookstore.ITs;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.project.bookstore.BaseTest;
import com.project.bookstore.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Full-context integration test: proves the real Spring application context wires
 * {@code CloudinaryConfig} -> {@link Cloudinary} -> {@link CloudinaryService} together.
 * Only the Cloudinary SDK's outbound HTTP call is stubbed via {@code @MockBean Cloudinary};
 * everything else (component scan, DI, property binding) is the real thing.
 * <p>
 * Inherits the shared Testcontainers MySQL instance from {@link BaseTest}.
 */
class CloudinaryServiceIntegrationTest extends BaseTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    void uploadImage_isResolvedFromContext_andReturnsSecureUrlFromSdk() throws IOException {
        final Uploader uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), anyMap())).thenReturn(ObjectUtils.asMap(
                "secure_url", "https://res.cloudinary.com/demo/image/upload/v1/book_covers/x.jpg"));

        final var file = new MockMultipartFile("file", "x.jpg", "image/jpeg", "img-bytes".getBytes());

        final var url = cloudinaryService.uploadImage(file);

        assertThat(url).isEqualTo("https://res.cloudinary.com/demo/image/upload/v1/book_covers/x.jpg");

        final ArgumentCaptor<Map> options = ArgumentCaptor.forClass(Map.class);
        verify(uploader).upload(any(), options.capture());
        assertThat(options.getValue()).containsEntry("folder", "book_covers");
    }
}
