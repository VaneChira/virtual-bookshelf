package com.project.bookstore;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDate;

/**
 * Base class for every integration test.
 * <p>
 * A single MySQL {@link MySQLContainer} is started once for the whole test JVM (the
 * Testcontainers "singleton container" pattern) and shared by all subclasses, so the
 * tests no longer depend on a hand-configured local MySQL instance. Schema and seed
 * data come from {@code src/test/resources/db/init.sql}; connection settings are handed
 * to Spring through {@link DynamicPropertySource}, overriding whatever is in
 * {@code application-test.properties}.
 * <p>
 * Requires a running Docker daemon.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseTest {

    public static final long PROGRESS_PAGE = 120L;
    public final static Long USER_ID = 1L;
    public final static Long BOOK_ID = 1L;
    public final static Integer RATING = 5;
    public final static String COMMENT = "Nice book";
    public final static LocalDate LOCAL_DATE = LocalDate.ofYearDay(2021, 6);
    public final static long HISTORY_GENRE_ID = 1;

    protected static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("bookstore_test")
                    .withUsername("bookstore")
                    .withPassword("bookstore")
                    .withConfigurationOverride("mysql-conf")
                    .withInitScript("db/init.sql");

    static {
        MYSQL.start();
    }

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }
}
