package com.testing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;
    private String isbn;
    @Column( name = "published_year")
    private int publishedYear;

    private String genre;

    private BigDecimal price;

    private Boolean available;

    @ElementCollection
    @CollectionTable(name = "book_ratings"
            , joinColumns = @JoinColumn(name = "book_id"))
    private List<Integer> ratings;

}
