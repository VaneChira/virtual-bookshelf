package com.project.bookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="book_title")
    private String bookTitle;

    @Column(name="author")
    private String author;

    @Column(name="description")
    private String description;

    @ManyToMany
    @JoinTable(
            name="genres_in_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id"))
    @JsonIgnoreProperties("booksForGenre")
    Set<Genre> genresInBooks;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="pages")
    private Integer pages;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", genresInBooks=" + genresInBooks +
                ", imageUrl='" + imageUrl + '\'' +
                ", pages=" + pages +
                '}';
    }

    public Book(Long id, String bookTitle, String author, String description, Set<Genre> genresInBooks, String imageUrl, Integer pages) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.description = description;
        this.genresInBooks = genresInBooks;
        this.imageUrl = imageUrl;
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Genre> getGenresInBooks() {
        return genresInBooks;
    }

    public void setGenresInBooks(Set<Genre> genresInBooks) {
        this.genresInBooks = genresInBooks;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

}
