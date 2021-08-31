package com.project.bookstore.repository;

import com.project.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM book b\n" +
            " INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=1 AND ub.user_id=:userId", nativeQuery = true)
    Long getWishlistCountByUserId(Long userId);

    @Query(value = "SELECT COUNT(*) FROM book b\n" +
            " INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=2 AND ub.user_id=:userId", nativeQuery = true)
    Long getCurrentlyReadingCountByUserId(Long userId);

    @Query(value = "SELECT COUNT(*) FROM book b\n" +
            " INNER JOIN user_book ub ON b.id=ub.book_id WHERE ub.book_state=3 AND ub.user_id=:userId", nativeQuery = true)
    Long getReadCountByUserId(Long userId);
}
