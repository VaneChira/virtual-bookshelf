package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.security.AdminBootstrapRunner;
import com.project.bookstore.security.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * {@link AdminBootstrapRunner} is excluded from the "test" profile (it needs
 * admin.bootstrap.* properties that don't exist there), so it's never a Spring bean
 * in this context. It's built by hand here instead, wired to the real Testcontainers
 * repositories, to verify its behavior against a real database.
 */
public class AdminBootstrapRunnerIntegrationTest extends BaseTest {

    private static final String ADMIN_EMAIL = "bootstrap.admin.test@example.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void when_runTwice_then_onlyOneAdminUserIsCreated() {
        final var runner = new AdminBootstrapRunner(userRepository, roleRepository, passwordEncoder);
        ReflectionTestUtils.setField(runner, "adminEmail", ADMIN_EMAIL);
        ReflectionTestUtils.setField(runner, "adminPassword", "bootstrap-password");

        try {
            runner.run();
            final var createdAdmin = userRepository.findByEmail(ADMIN_EMAIL);
            assert (createdAdmin != null);
            assert (createdAdmin.getRoles().iterator().next().getName().equals(RoleName.ROLE_ADMIN));

            runner.run();
            final var afterSecondRun = userRepository.findByEmail(ADMIN_EMAIL);
            assert (afterSecondRun.getId().equals(createdAdmin.getId()));
        } finally {
            final var admin = userRepository.findByEmail(ADMIN_EMAIL);
            if (admin != null) {
                userRepository.deleteById(admin.getId());
            }
        }
    }
}
