package com.testing.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String author;

    private String isbn;

    @Column(name = "published_year")
    private int publishedYear;

    private String genre;

    private BigDecimal price;

    private Boolean available;

    @ElementCollection
    @CollectionTable(name = "book_ratings"
            , joinColumns = @JoinColumn(name = "book_id"))
    private List<Integer> ratings; // 4, 5, 6, 7, 8,

    public Book() {
    }

    public Book(long id, String title, String author, String isbn, int publishedYear, String genre, BigDecimal price, Boolean available, List<Integer> ratings) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.price = price;
        this.available = available;
        this.ratings = ratings;
    }
}