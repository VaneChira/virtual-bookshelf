package com.project.bookstore.repository;

import com.project.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book b WHERE CONCAT(b.book_title,' ') LIKE %:keyword%", nativeQuery = true)
    Set<Book> search(String keyword);

    @Query(value = "SELECT * from book b\n" +
            "INNER JOIN genres_in_books gib\n" +
            "ON b.id = gib.book_id\n" +
            "INNER JOIN genres g\n" +
            "ON gib.genre_id=g.id\n" +
            "WHERE g.type=:genre AND b.id!=:bookId LIMIT 4", nativeQuery = true)
    List<Book> relatedBooksBasedOnGender(Long bookId, String genre);

    @Query(value = "SELECT * FROM book b\n" +
            "INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=1 AND ub.user_id=:userId", nativeQuery = true)
    List<Book> findAllWishlistByUser(Long userId);

    @Query(value = "SELECT * FROM book b\n" +
            "INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=2 AND ub.user_id=:userId", nativeQuery = true)
    List<Book> findAllCurrentlyReadingByUser(Long userId);

    @Query(value = "SELECT * FROM book b\n" +
            "INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=3 AND ub.user_id=:userId", nativeQuery = true)
    List<Book> findAllReadByUser(Long userId);

    @Query(value = "SELECT * FROM book b\n" +
            "INNER JOIN user_book ub ON b.id=ub.book_id WHERE (ub.book_state=2 OR ub.book_state=3) AND ub.user_id=:userId", nativeQuery = true)
    List<Book> findAllCurrentlyReadingAndReadByUser(Long userId);

    @Query(value = "SELECT * FROM book b\n" +
            "INNER JOIN user_book ub ON b.id=ub.book_id WHERE (ub.book_state=1 OR ub.book_state=2 OR ub.book_state=3) AND ub.user_id=:userId", nativeQuery = true)
    List<Book> findAllStatedBooksByUser(Long userId);

}
