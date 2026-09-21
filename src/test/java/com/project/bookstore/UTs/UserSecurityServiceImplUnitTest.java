package com.project.bookstore.UTs;

import com.project.bookstore.model.Role;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.security.UserRegistrationFormEntity;
import com.project.bookstore.security.UserSecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pure unit test for {@link UserSecurityServiceImpl}: repositories and the password
 * encoder are fully mocked, no Spring context and no database.
 */
@ExtendWith(MockitoExtension.class)
class UserSecurityServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserSecurityServiceImpl userSecurityService;

    private final UserRegistrationFormEntity form =
            new UserRegistrationFormEntity("Jane", "Doe", "jane.doe@example.com", "plain-password");

    @BeforeEach
    void setUp() {
        userSecurityService = new UserSecurityServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void save_encodesPasswordAndCopiesFormFieldsOntoUser() {
        final var existingRoleUser = new Role("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(existingRoleUser);
        when(passwordEncoder.encode("plain-password")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final var savedUser = userSecurityService.save(form);

        assertThat(savedUser.getName()).isEqualTo("Jane");
        assertThat(savedUser.getLastName()).isEqualTo("Doe");
        assertThat(savedUser.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("encoded-password");
    }

    @Test
    void save_attachesExistingRoleUserInsteadOfCreatingANewOne() {
        final var existingRoleUser = new Role("ROLE_USER");
        existingRoleUser.setId(1L);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(existingRoleUser);
        when(passwordEncoder.encode(any())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final var savedUser = userSecurityService.save(form);

        verify(roleRepository).findByName(eq("ROLE_USER"));
        assertThat(savedUser.getRoles()).containsExactly(existingRoleUser);
        assertThat(savedUser.getRoles()).extracting(Role::getId).containsOnly(1L);
    }
}
