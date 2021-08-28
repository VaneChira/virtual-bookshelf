package com.project.bookstore.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="feedback")
public class Feedback {

    @EmbeddedId
    FeedbackKey feedbackKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @Column(name = "rating")
    Integer rating;

    @Column(name = "comment")
    String comment;

    @Column(name = "date")
    LocalDate date;

    public Feedback() {
    }

    public Feedback(FeedbackKey feedbackKey, User user, Book book, Integer rating, String comment, LocalDate date) {
        this.feedbackKey = feedbackKey;
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public FeedbackKey getFeedbackKey() {
        return feedbackKey;
    }

    public void setFeedbackKey(FeedbackKey feedbackKey) {
        this.feedbackKey = feedbackKey;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
