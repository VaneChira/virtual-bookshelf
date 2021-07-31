package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name="wish_list")
public class WishList {

    @EmbeddedId
    WishListKey wishListKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    public WishList() {
    }

    public WishList(WishListKey wishListKey, User user, Book book) {
        this.wishListKey = wishListKey;
        this.user = user;
        this.book = book;
    }

    public WishListKey getWishListKey() {
        return wishListKey;
    }

    public void setWishListKey(WishListKey wishListKey) {
        this.wishListKey = wishListKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wishListKey=" + wishListKey +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
