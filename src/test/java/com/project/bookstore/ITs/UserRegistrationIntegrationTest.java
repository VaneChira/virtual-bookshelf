package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.security.UserRegistrationFormEntity;
import com.project.bookstore.security.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    private long countRoleUserRows() {
        return roleRepository.findAll().stream()
                .filter(role -> "ROLE_USER".equals(role.getName()))
                .count();
    }
}
