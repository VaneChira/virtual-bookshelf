package com.project.bookstore.repository;

import com.project.bookstore.model.Feedback;
import com.project.bookstore.model.FeedbackKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackKey> {
    @Query(value = "SELECT * FROM Feedback WHERE book_id=:bookId", nativeQuery = true)
    List<Feedback> getFeedbacksByBook(Long bookId);

    @Query(value = "SELECT AVG(f.rating) FROM Feedback f WHERE book_id=:bookId", nativeQuery = true)
    Float getAverageRating(Long bookId);

    @Query(value = "SELECT COUNT(*) FROM Feedback f WHERE book_id = :bookId", nativeQuery = true)
    Integer getNumberOfRatings(Long bookId);

}
