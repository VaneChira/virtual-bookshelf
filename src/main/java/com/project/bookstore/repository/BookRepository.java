package com.project.bookstore.repository;

import com.project.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
    //This is JPA Repository for books.

    @Query(value = "SELECT * FROM book b WHERE CONCAT(b.book_title,' ', b.author) LIKE %:keyword%", nativeQuery = true)
    List<Book> search(String keyword);
}
