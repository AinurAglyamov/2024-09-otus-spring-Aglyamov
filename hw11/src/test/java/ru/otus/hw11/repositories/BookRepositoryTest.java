package ru.otus.hw11.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;
import reactor.test.StepVerifier;
import ru.otus.hw11.models.Book;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepositoryCustom bookRepository;

    @Test
    @DisplayName(" должен найти книгу по id")
    void shouldFindById() {
        long bookId = 1L;

        StepVerifier.create(bookRepository.findById(bookId))
                .assertNext(book ->
                        assertThat(book).isNotNull()
                                .matches(b -> StringUtils.hasText(b.getTitle()))
                                .matches(b -> Objects.nonNull(b.getGenreId()))
                                .matches(b -> Objects.nonNull(b.getAuthorId()))
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен найти все книги")
    void shouldFindAll() {
        List<Book> books = bookRepository.findAll().collectList().block();

        assertThat(books).isNotNull()
                .allMatch(book -> StringUtils.hasText(book.getTitle()))
                .allMatch(book -> Objects.nonNull(book.getAuthorId()))
                .allMatch(book -> Objects.nonNull(book.getGenreId()));
    }

    @Test
    @DisplayName(" должен создать новую книгу")
    void shouldInsert() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = 2L;

        Book book = new Book()
                .setTitle(bookTitle)
                .setAuthorId(authorId)
                .setGenreId(genreId);

        StepVerifier.create(bookRepository.save(book))
                .assertNext(savedBook ->
                        assertThat(savedBook).isNotNull()
                                .matches(b -> StringUtils.hasText(b.getTitle()))
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен обновить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdate() {
        long bookId = 1L;
        String title = "Гарри Топор и Медаль Феликса";
        long authorId = 1L;
        long genreId = 1L;

        Book book = new Book()
                .setId(bookId)
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenreId(genreId);

        StepVerifier.create(bookRepository.save(book))
                .assertNext(savedBook ->
                        assertThat(savedBook).isNotNull()
                                .matches(b -> StringUtils.hasText(b.getTitle()))
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        long bookId = 1L;

        StepVerifier.create(bookRepository.deleteById(bookId))
                .expectComplete()
                .verify();
    }
}