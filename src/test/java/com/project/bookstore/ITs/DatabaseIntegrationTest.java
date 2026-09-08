package com.project.bookstore.ITs;

import com.project.bookstore.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke test proving the shared Testcontainers MySQL instance is up and that Spring's
 * {@code DataSource} is wired to it with the schema and seed data from
 * {@code db/init.sql} loaded.
 */
public class DatabaseIntegrationTest extends BaseTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void containerIsRunning() {
        assertTrue(MYSQL.isRunning());
    }

    @Test
    void springIsConnectedToTheContainer() {
        String url = jdbcTemplate.execute(
                (org.springframework.jdbc.core.ConnectionCallback<String>) c -> c.getMetaData().getURL());
        assertTrue(url.contains(String.valueOf(MYSQL.getFirstMappedPort())),
                "Spring DataSource should point at the Testcontainers MySQL port, but was: " + url);
    }

    @Test
    void seedDataIsLoaded() {
        Integer users = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        Integer genres = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM genres", Integer.class);
        assertEquals(2, users);
        assertEquals(3, genres);
    }
}
