package com.testing.repository;

import com.testing.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
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
    void x(){
        // given
        // when
        Book saved = bookRepository.save(book);

        // then
        assertThat(saved).isNotNull();
    }
}