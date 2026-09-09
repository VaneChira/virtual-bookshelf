package com.project.bookstore.UTs;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.project.bookstore.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pure unit test for {@link CloudinaryService}: the Cloudinary SDK is fully mocked,
 * no Spring context and no network access.
 */
@ExtendWith(MockitoExtension.class)
class CloudinaryServiceUnitTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    private CloudinaryService cloudinaryService;

    private final MultipartFile file =
            new MockMultipartFile("file", "cover.jpg", "image/jpeg", "fake-image-bytes".getBytes());

    @BeforeEach
    void setUp() {
        cloudinaryService = new CloudinaryService(cloudinary);
    }

    @Test
    void uploadImage_returnsSecureUrlFromCloudinaryResponse() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), anyMap())).thenReturn(ObjectUtils.asMap(
                "secure_url", "https://res.cloudinary.com/demo/image/upload/v1/book_covers/cover.jpg",
                "url", "http://res.cloudinary.com/demo/image/upload/v1/book_covers/cover.jpg",
                "public_id", "book_covers/cover"));

        final var url = cloudinaryService.uploadImage(file);

        assertThat(url).isEqualTo("https://res.cloudinary.com/demo/image/upload/v1/book_covers/cover.jpg");
    }

    @Test
    void uploadImage_sendsRawBytesAndBookCoversFolderToCloudinary() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), anyMap()))
                .thenReturn(ObjectUtils.asMap("secure_url", "https://res.cloudinary.com/demo/x.jpg"));

        final ArgumentCaptor<Object> payload = ArgumentCaptor.forClass(Object.class);
        final ArgumentCaptor<Map> options = ArgumentCaptor.forClass(Map.class);

        cloudinaryService.uploadImage(file);

        verify(uploader).upload(payload.capture(), options.capture());
        assertThat((byte[]) payload.getValue()).isEqualTo("fake-image-bytes".getBytes());
        assertThat(options.getValue()).containsEntry("folder", "book_covers");
    }

    @Test
    void uploadImage_propagatesIOExceptionFromCloudinary() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), anyMap())).thenThrow(new IOException("network down"));

        assertThatThrownBy(() -> cloudinaryService.uploadImage(file))
                .isInstanceOf(IOException.class)
                .hasMessage("network down");
    }
}
