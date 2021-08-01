package com.project.bookstore.repository;

import com.project.bookstore.model.WishList;
import com.project.bookstore.model.WishListKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListKey> {

    List<WishList> findAllByUserEmail(String email);
}
