package com.project.bookstore.repository;

import com.project.bookstore.model.UserBook;
import com.project.bookstore.model.UserBookKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, UserBookKey> {
//    List<Product> findByIdIn(List<Long> ids);
//      ... countBy...(...)
//    Optional<Product> findByExternalId(Long externalId);

}
