package com.testing.repository;

import com.testing.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);
    List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear);
}
