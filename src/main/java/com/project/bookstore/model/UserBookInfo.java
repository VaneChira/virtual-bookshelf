package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name="user_book")
public class UserBookInfo {
    @EmbeddedId
    UserBookKey userBookKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @Column(name = "user_rating")
    Float userRating;

    @Column(name = "progress_pages")
    Long progressPage;

    @Column(name = "book_state")
    Integer bookState;

    public UserBookInfo() {
    }


    public UserBookInfo(UserBookKey userBookKey, User user, Book book, Integer bookState) {
        this.userBookKey = userBookKey;
        this.user = user;
        this.book = book;
        this.bookState = bookState;
    }

    public UserBookInfo(UserBookKey userBookKey, User user, Book book, Float userRating, Long progressPage, Integer bookState) {
        this.userBookKey = userBookKey;
        this.user = user;
        this.book = book;
        this.userRating = userRating;
        this.progressPage = progressPage;
        this.bookState = bookState;
    }

    public UserBookKey getUserBookKey() {
        return userBookKey;
    }

    public void setUserBookKey(UserBookKey userBookKey) {
        this.userBookKey = userBookKey;
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

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

    public Long getProgressPage() {
        return progressPage;
    }

    public void setProgressPage(Long progressPage) {
        this.progressPage = progressPage;
    }

    public Integer getBookState() {
        return bookState;
    }

    public void setBookState(Integer bookState) {
        this.bookState = bookState;
    }
}
