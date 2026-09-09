package com.project.bookstore.UTs;

import com.cloudinary.Cloudinary;
import com.project.bookstore.configs.CloudinaryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pure unit test for {@link CloudinaryConfig}: builds the bean by hand with fake
 * credentials and checks the resulting {@link Cloudinary} configuration.
 */
class CloudinaryConfigUnitTest {

    @Test
    void cloudinaryBean_isBuiltFromConfiguredCredentialsAndIsSecure() {
        final var config = new CloudinaryConfig();
        ReflectionTestUtils.setField(config, "cloudName", "demo-cloud");
        ReflectionTestUtils.setField(config, "apiKey", "123456789");
        ReflectionTestUtils.setField(config, "apiSecret", "top-secret");

        final Cloudinary cloudinary = config.cloudinary();

        assertThat(cloudinary).isNotNull();
        assertThat(cloudinary.config.cloudName).isEqualTo("demo-cloud");
        assertThat(cloudinary.config.apiKey).isEqualTo("123456789");
        assertThat(cloudinary.config.apiSecret).isEqualTo("top-secret");
        assertThat(cloudinary.config.secure).isTrue();
    }
}
