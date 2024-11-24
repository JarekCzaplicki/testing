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
    @CollectionTable(name = "book_ratings" // nazwa tabeli, jaka będzie przechowywać elementy kolekcji 'rating'
            , joinColumns = @JoinColumn(name = "book_id"))
// określa kolumnę w tabeli 'book_ratings', która będzie służyła za klucz obcy (foreign key) do identyfikacji powiązania z encją nadrzędną, czyli z 'Book'
    private List<Integer> ratings;
}