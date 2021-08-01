package com.project.bookstore.repository;

import com.project.bookstore.model.UserBookInfo;
import com.project.bookstore.model.UserBookKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBookInfo, UserBookKey> {

//    List<UserBook> findByIdUser (String user);
//    List<Product> findByIdIn(List<Long> ids);
//      ... countBy...(...)
//    Optional<Product> findByExternalId(Long externalId);
    List<UserBookInfo> findAllByUserEmail(String email);



}
