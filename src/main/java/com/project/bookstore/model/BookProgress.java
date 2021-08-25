package com.project.bookstore.model;


import javax.persistence.*;

@Entity
@Table(name="user_book")
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

    public BookProgress() {
    }


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

    public BookProgressKey getBookProgressKey() {
        return bookProgressKey;
    }

    public void setBookProgressKey(BookProgressKey bookProgressKey) {
        this.bookProgressKey = bookProgressKey;
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
