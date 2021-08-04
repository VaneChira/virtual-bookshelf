package com.project.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="type")
    private String type;

    @ManyToMany(mappedBy = "genresInBooks")
    @JsonIgnoreProperties("genresInBooks")
    Set<Book> booksForGenre;

    public Genre() {
    }

    public Genre(Long id, String type, Set<Book> booksForGenre) {
        this.id = id;
        this.type = type;
        this.booksForGenre = booksForGenre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Book> getBooksForGenre() {
        return booksForGenre;
    }

    public void setBooksForGenre(Set<Book> booksForGenre) {
        this.booksForGenre = booksForGenre;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
