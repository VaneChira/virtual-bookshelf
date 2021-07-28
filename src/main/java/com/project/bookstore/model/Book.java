package com.project.bookstore.model;


import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="book_title")
    private String bookTitle;

    @Column(name="author")
    private String author;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private int rating;

    @Column(name="number_of_ratings")
    private int numberOfRatings;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="pages")
    private int pages;

    @Column(name="stock")
    private int stock;

    public Book() {
    }

    public Book(int id, String bookTitle, String author, String description, int rating, int numberOfRatings, String imageUrl, int pages, int stock) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
                ", rating=" + rating +
                ", numberOfRatings=" + numberOfRatings +
                ", imageUrl='" + imageUrl + '\'' +
                ", pages=" + pages +
                ", stock=" + stock +
                '}';
    }
}
