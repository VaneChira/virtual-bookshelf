package com.project.bookstore.model;


import javax.persistence.*;

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

    @Column(name="genre")
    private String genre;

    @Column(name="rating")
    private Float rating;

    @Column(name="number_of_ratings")
    private Integer numberOfRatings;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="pages")
    private Integer pages;

    @Column(name="stock")
    private Integer stock;

    public Book() {
    }

    public Book(Long id, String bookTitle, String author, String description, Float rating, int numberOfRatings, String imageUrl, int pages, int stock) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.imageUrl = imageUrl;
        this.pages = pages;
        this.stock = stock;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", genres='" + genre + '\'' +
                ", rating=" + rating +
                ", numberOfRatings=" + numberOfRatings +
                ", imageUrl='" + imageUrl + '\'' +
                ", pages=" + pages +
                ", stock=" + stock +
                '}';
    }
}
