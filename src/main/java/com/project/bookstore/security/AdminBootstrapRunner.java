package com.project.bookstore.security;

import com.project.bookstore.model.User;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.List.of;

/**
 * Creates the initial ROLE_ADMIN user on application startup, from
 * admin.bootstrap.email / admin.bootstrap.password, since there's no registration
 * path that can grant ROLE_ADMIN. Create-only and idempotent: if a user with that
 * email already exists, nothing happens, so it's safe to run on every restart.
 * Excluded from the "test" profile, which has no admin.bootstrap.* properties.
 */
@Component
@Profile("!test")
public class AdminBootstrapRunner implements CommandLineRunner {
    @Value("${admin.bootstrap.email}")
    private String adminEmail;
    @Value("${admin.bootstrap.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminBootstrapRunner(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(adminEmail) != null) {
            return;
        }

        final var admin = new User();
        admin.setName("Admin");
        admin.setLastName("Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(of(roleRepository.findByName(RoleName.ROLE_ADMIN)));

        userRepository.save(admin);
    }
}
