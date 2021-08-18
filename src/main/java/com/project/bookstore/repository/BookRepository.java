package com.project.bookstore.repository;

import com.project.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
    //This is JPA Repository for books.

    @Query(value = "SELECT * FROM book b WHERE CONCAT(b.book_title,' ') LIKE %:keyword%", nativeQuery = true)
    List<Book> search(String keyword);

    @Query(value = "SELECT * from book b\n" +
            "INNER JOIN genres_in_books gib\n" +
            "ON b.id = gib.book_id\n" +
            "INNER JOIN genres g\n" +
            "ON gib.genre_id=g.id\n" +
            "WHERE g.type=:genre AND b.id!=:bookId LIMIT 4", nativeQuery = true)
    List<Book> relatedBooksBasedOnGender(Long bookId, String genre);
}
