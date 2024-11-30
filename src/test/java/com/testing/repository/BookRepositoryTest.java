package com.testing.repository;

import com.testing.entity.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager entityManager;

    private Book book;

    @BeforeEach
    void setUp() {
        entityManager.createNativeQuery(
                "ALTER TABLE books ALTER COLUMN id RESTART WITH 1"
        ).executeUpdate();
        book = new Book(
                1L,
                "Terminator",
                "Alex",
                "1234567",
                1999,
                "SyFy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 7, 8, 2, 1)
        );
    }
    @DisplayName("Saving book test")
    @Test
    void testSavedBook() {
        // given
        // when
        Book saved = bookRepository.save(book);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
        assertThat(saved).isEqualTo(book);
    }

    @DisplayName("Finding all books")
    @Test
    void testFindAllBooks() {
        // given
        bookRepository.save(book);
        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertThat(books).isNotNull();
        assertThat(books.size()).isBetween(1, 2);
    }

    @DisplayName("Finding book by id")
    @Test
    void testFindById() {
        // given
        Book saved = bookRepository.save(book);
        // when
        Optional<Book> bookOptional = bookRepository.findById(saved.getId());

        // then
        assertThat(bookOptional).isPresent();
        assertThat(bookOptional).isEqualTo(Optional.of(book));
    }

    @DisplayName("Updating a book")
    @Test
    void testUpdateBook() {
        // given
        Book saved = bookRepository.save(book);
        saved.setTitle("Zmieniony tytuł");
        // when
        Book updatedBook = bookRepository.save(saved);

        // then
        assertThat(updatedBook.getTitle()).isEqualTo("Zmieniony tytuł");
    }


    @Test
    void testDeleteBook() {
        // given
        Book saved = bookRepository.save(book);

        // when
        bookRepository.delete(saved);

        // then
        Optional<Book> byId = bookRepository.findById(saved.getId());
        assertThat(byId).isNotPresent();
    }


    @Test
    void testFindByGenre() {
        // given
        String genre = bookRepository.save(book).getGenre();

        // when
        List<Book> books = bookRepository.findByGenre(genre);

        // then
        assertThat(books).isNotNull();
        assertThat(books.get(0).getGenre()).isEqualTo(genre);
    }
     @Test
    void testFindBooksByAuthorAndPublishedYear() {
        // given
        Book saved = bookRepository.save(book);
        String author = saved.getAuthor();
        int publishedYear = book.getPublishedYear();

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(
                b ->
                        b.getAuthor().equals(author)
                                &&
                                b.getPublishedYear() == publishedYear
        );
    }
    @Test
    void testFindBooksByAuthorAndPublishedYearNegativeScenario() {
        // given
        bookRepository.save(book);
        String author = "Inny autor";
        int publishedYear = 1000;

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isEmpty();
    }

}