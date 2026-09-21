package com.project.bookstore.UTs;

import com.project.bookstore.model.Role;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.security.AdminBootstrapRunner;
import com.project.bookstore.security.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pure unit test for {@link AdminBootstrapRunner}: repositories and the password
 * encoder are fully mocked, no Spring context and no database.
 */
@ExtendWith(MockitoExtension.class)
class AdminBootstrapRunnerUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private AdminBootstrapRunner adminBootstrapRunner;

    @BeforeEach
    void setUp() {
        adminBootstrapRunner = new AdminBootstrapRunner(userRepository, roleRepository, passwordEncoder);
        ReflectionTestUtils.setField(adminBootstrapRunner, "adminEmail", "admin@example.com");
        ReflectionTestUtils.setField(adminBootstrapRunner, "adminPassword", "raw-password");
    }

    @Test
    void run_createsAdminUser_whenNoUserWithThatEmailExists() {
        final var adminRole = new Role(RoleName.ROLE_ADMIN);
        adminRole.setId(2L);
        when(userRepository.findByEmail("admin@example.com")).thenReturn(null);
        when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(adminRole);
        when(passwordEncoder.encode("raw-password")).thenReturn("encoded-password");

        adminBootstrapRunner.run();

        final var captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        final var savedAdmin = captor.getValue();
        assertThat(savedAdmin.getEmail()).isEqualTo("admin@example.com");
        assertThat(savedAdmin.getPassword()).isEqualTo("encoded-password");
        assertThat(savedAdmin.getRoles()).containsExactly(adminRole);
    }

    @Test
    void run_doesNothing_whenAnAdminUserAlreadyExists() {
        final var existingAdmin = new User();
        existingAdmin.setEmail("admin@example.com");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(existingAdmin);

        adminBootstrapRunner.run();

        verify(userRepository, never()).save(any(User.class));
    }
}
