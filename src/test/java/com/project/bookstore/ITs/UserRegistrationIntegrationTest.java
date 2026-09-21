package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.exception.DuplicateEmailException;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.security.RoleName;
import com.project.bookstore.security.UserRegistrationFormEntity;
import com.project.bookstore.security.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserRegistrationIntegrationTest extends BaseTest {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void when_multipleUsersRegister_then_theyShareTheSameRoleUserRow() {
        final var roleUserCountBefore = countRoleUserRows();

        final var userA = userSecurityService.save(
                new UserRegistrationFormEntity("Test", "UserA", "test.user.a@example.com", "password"));
        final var userB = userSecurityService.save(
                new UserRegistrationFormEntity("Test", "UserB", "test.user.b@example.com", "password"));

        try {
            final var roleUserCountAfter = countRoleUserRows();
            assert (roleUserCountBefore == roleUserCountAfter);

            final var roleIdForA = userA.getRoles().iterator().next().getId();
            final var roleIdForB = userB.getRoles().iterator().next().getId();
            assert (roleIdForA.equals(roleIdForB));
        } finally {
            userRepository.deleteById(userA.getId());
            userRepository.deleteById(userB.getId());
        }
    }

    @Test
    void when_registeringWithAnEmailThatAlreadyExists_then_duplicateEmailExceptionIsThrown() {
        final var email = "test.duplicate@example.com";
        final var firstUser = userSecurityService.save(
                new UserRegistrationFormEntity("Test", "First", email, "password"));

        try {
            assertThatThrownBy(() -> userSecurityService.save(
                    new UserRegistrationFormEntity("Test", "Second", email, "password")))
                    .isInstanceOf(DuplicateEmailException.class);

            final var usersWithThatEmail = userRepository.findAll().stream()
                    .filter(user -> email.equals(user.getEmail()))
                    .count();
            assert (usersWithThatEmail == 1);
        } finally {
            userRepository.deleteById(firstUser.getId());
        }
    }

    private long countRoleUserRows() {
        return roleRepository.findAll().stream()
                .filter(role -> RoleName.ROLE_USER.equals(role.getName()))
                .count();
    }
}
