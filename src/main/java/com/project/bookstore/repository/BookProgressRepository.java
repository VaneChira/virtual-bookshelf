package com.project.bookstore.repository;

import com.project.bookstore.model.BookProgress;
import com.project.bookstore.model.BookProgressKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookProgressRepository extends JpaRepository<BookProgress, BookProgressKey> {
    List<BookProgress> findAllByUserEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM user_book f WHERE book_state=1 AND user_id=:userId", nativeQuery = true)
    Integer getNumberOfWishlistBooks(Long userId);

    @Query(value = "SELECT COUNT(*) FROM user_book f WHERE book_state=2 AND user_id=:userId", nativeQuery = true)
    Integer getNumberOfCurrentlyReadingBooks(Long userId);

    @Query(value = "SELECT COUNT(*) FROM user_book f WHERE book_state=3 AND user_id=:userId", nativeQuery = true)
    Integer getNumberOfReadBooks(Long userId);

}
