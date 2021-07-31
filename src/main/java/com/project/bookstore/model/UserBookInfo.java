package com.project.bookstore.model;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name="user_rating")
    Float userRating;

    @Column(name="progress_pages")
    Long progressPage;

    @Column(name="date_bought")
    Date boughtDate;

    public UserBookInfo() {
    }

    public UserBookInfo(UserBookKey userBookKey, User user, Book book, Float userRating, Long progressPage, Date boughtDate) {
        this.userBookKey = userBookKey;
        this.user = user;
        this.book = book;
        this.userRating = userRating;
        this.progressPage = progressPage;
        this.boughtDate = boughtDate;
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

    public Date getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    @Override
    public String toString() {
        return "UserBookInfo{" +
                "userBookKey=" + userBookKey +
                ", user=" + user +
                ", book=" + book +
                ", userRating=" + userRating +
                ", progressPage=" + progressPage +
                ", boughtDate=" + boughtDate +
                '}';
    }
}
