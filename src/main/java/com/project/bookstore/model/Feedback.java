package com.project.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feedback")
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

    public Feedback(FeedbackKey feedbackKey, User user, Book book, Integer rating, String comment, LocalDate date) {
        this.feedbackKey = feedbackKey;
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }
}
