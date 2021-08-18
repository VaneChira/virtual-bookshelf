package com.project.bookstore.repository;


import com.project.bookstore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM genres g\n" +
            "INNER JOIN genres_in_books gib\n" +
            "ON g.id = gib.genre_id\n" +
            "INNER JOIN book b\n" +
            "ON b.id = gib.book_id;\n", nativeQuery = true)
    Set<Genre> getAllUsedGenres();
}
