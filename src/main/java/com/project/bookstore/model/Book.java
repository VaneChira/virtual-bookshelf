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

    @ManyToMany
    @JoinTable(
            name="books_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id"))
    @JsonIgnoreProperties("booksForAuthors")
    Set<Author> authorInBooks;

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
    private Long pages;

    @Column(name="year")
    private Integer year;

    @Column(name="language")
    private String language;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", description='" + description + '\'' +
                ", genresInBooks=" + genresInBooks +
                ", imageUrl='" + imageUrl + '\'' +
                ", pages=" + pages +
                ", year=" + year +
                ", language='" + language + '\'' +
                '}';
    }

    public Book(Long id, String bookTitle, Set<Author> authorInBooks, String description, Set<Genre> genresInBooks, String imageUrl, Long pages, Integer year, String language) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorInBooks = authorInBooks;
        this.description = description;
        this.genresInBooks = genresInBooks;
        this.imageUrl = imageUrl;
        this.pages = pages;
        this.year = year;
        this.language = language;
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

    public Set<Author> getAuthorInBooks() {
        return authorInBooks;
    }

    public void setAuthorInBooks(Set<Author> authorInBooks) {
        this.authorInBooks = authorInBooks;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
