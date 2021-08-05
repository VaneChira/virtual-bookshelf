package com.project.bookstore.repository;

import com.project.bookstore.model.UserBookInfo;
import com.project.bookstore.model.UserBookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBookInfo, UserBookKey> {

    List<UserBookInfo> findAllByUserEmail(String email);

    @Query(value = "SELECT * FROM user_book WHERE book_state=1 AND user_id=:userId", nativeQuery = true)
    List<UserBookInfo> findAllWishlistByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=2 AND user_id=:userId", nativeQuery = true)
    List<UserBookInfo> findAllCurrentlyReadingByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=3 AND user_id=:userId", nativeQuery = true)
    List<UserBookInfo> findAllReadByUser(Long userId);

    @Query(value = "SELECT * FROM user_book WHERE book_state=2 OR book_state=3 AND user_id=:userId", nativeQuery = true)
    List<UserBookInfo> findAllCurrentlyReadingAndReadByUser(Long userId);
}
