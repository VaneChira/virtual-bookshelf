package com.project.bookstore.repository;

import com.project.bookstore.model.BookProgress;
import com.project.bookstore.model.BookProgressKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookProgressRepository extends JpaRepository<BookProgress, BookProgressKey> {

    List<BookProgress> findAllByUserEmail(String email);

    @Query(value = "SELECT * FROM user_book WHERE book_state=1 AND user_id=:userId", nativeQuery = true)
    List<BookProgress> findAllWishlistByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=2 AND user_id=:userId", nativeQuery = true)
    List<BookProgress> findAllCurrentlyReadingByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=3 AND user_id=:userId", nativeQuery = true)
    List<BookProgress> findAllReadByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=2 OR book_state=3 AND user_id=:userId", nativeQuery = true)
    List<BookProgress> findAllCurrentlyReadingAndReadByUser(Long userId);

}
