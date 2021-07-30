package com.project.bookstore.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_book")
public class UserBook {
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

    @Column(name="date")
    Date boughtDate;
}
