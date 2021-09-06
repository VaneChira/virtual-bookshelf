package com.project.bookstore.rest;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class BookTestRest extends BaseTest {

    private final static String BOOKS_URL = "/api/books";
    @Autowired
    BookRepository bookRepository;
    @LocalServerPort
    private int port;

    @Test
    public void givenExistingId_whenGetCategoryById_thenReturnCategoryFromCSV() {
        Book testBook = new Book();
        Book addedBook = bookRepository.save(testBook);

        given()
                .port(port)
                .when()
                .delete(BOOKS_URL + "/deleteBookById/{id}", addedBook.getId())
                .then()
                .assertThat()
                .statusCode(200);
    }

}
