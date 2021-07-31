package com.project.bookstore.repository;

import com.project.bookstore.model.WishList;
import com.project.bookstore.model.WishListKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListKey> {
}
