package com.project.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
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

    @Column(name = "pages")
    private Long pages;

    @Column(name = "year")
    private Integer year;

    @Column(name = "language")
    private String language;

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
}
