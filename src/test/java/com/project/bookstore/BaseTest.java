package com.project.bookstore;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

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

}
