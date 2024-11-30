package com.testing.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

  }