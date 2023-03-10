package com.project.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_book")
public class BookProgress {
    @EmbeddedId
    BookProgressKey bookProgressKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @Column(name="progress_pages")
    Long progressPage;

    @Column(name = "book_state")
    Integer bookState;

    public BookProgress(BookProgressKey bookProgressKey, User user, Book book, Integer bookState) {
        this.bookProgressKey = bookProgressKey;
        this.user = user;
        this.book = book;
        this.bookState = bookState;
    }

    public BookProgress(BookProgressKey bookProgressKey, User user, Book book, Long progressPage, Integer bookState) {
        this.bookProgressKey = bookProgressKey;
        this.user = user;
        this.book = book;
        this.progressPage = progressPage;
        this.bookState = bookState;
    }
}
