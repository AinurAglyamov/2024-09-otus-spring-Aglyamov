package ru.otus.hw08.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw08.dto.BookDto;
import ru.otus.hw08.exceptions.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName(" должен найти книгу по id")
    void shouldFindById() {
        var bookId = new BigInteger("1");

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()))
                .matches(b -> Objects.nonNull(b.getGenre()))
                .matches(b -> Objects.nonNull(b.getAuthor()));
    }

    @Test
    @DisplayName(" должен найти все книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAll() {
        List<BookDto> books = bookService.findAll();

        int expectedNumberOfBooks = 3;

        assertThat(books).hasSize(expectedNumberOfBooks)
                .allMatch(book -> StringUtils.hasText(book.getTitle()))
                .allMatch(book -> Objects.nonNull(book.getAuthor()))
                .allMatch(book -> Objects.nonNull(book.getGenre()))
                .allMatch(book -> !CollectionUtils.isEmpty(book.getComments()));
    }

    @Test
    @DisplayName(" должен создать новую книгу")
    void shouldInsert() {
        String bookTitle = "Игрок";
        var authorId = new BigInteger("2");
        var genreId = new BigInteger("2");
        BookDto insertedBook = bookService.insert(bookTitle, authorId, genreId);

        Optional<BookDto> bookOptional = bookService.findById(insertedBook.getId());

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()) && b.getTitle().equals(bookTitle))
                .matches(b -> Objects.nonNull(b.getGenre()) && b.getGenre().getId().equals(genreId))
                .matches(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().getId().equals(authorId));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если автор не найден")
    void shouldThrowAnEntityNotFoundExceptionIfAuthorDoesNotExist() {
        String bookTitle = "Игрок";
        var authorId = new BigInteger("-2");
        var genreId = new BigInteger("2");

        String expectedMessage = "Author with id %d not found".formatted(authorId);

        assertThatThrownBy(() -> bookService.insert(bookTitle, authorId, genreId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если жанр не найден")
    void shouldThrowAnEntityNotFoundExceptionIfGenreDoesNotExist() {
        String bookTitle = "Игрок";
        var authorId = new BigInteger("2");
        var genreId = new BigInteger("-2");

        String expectedMessage = "Genre with id %d not found".formatted(genreId);

        assertThatThrownBy(() -> bookService.insert(bookTitle, authorId, genreId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить книгу")
    void shouldUpdate() {
        var bookId = new BigInteger("1");
        String title = "Гарри Топор и Медаль Феликса";
        var authorId = new BigInteger("1");
        var genreId = new BigInteger("1");

        BookDto book = bookService.findById(bookId).get();
        BookDto updatedBook = bookService.update(bookId, title, authorId, genreId);

        assertThat(updatedBook)
                .matches(ub -> ub.getId().equals(book.getId()))
                .matches(ub -> !ub.getTitle().equals(book.getTitle()));
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    void shouldDeleteById() {
        var bookId = new BigInteger("1");
        bookService.deleteById(bookId);

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isEmpty();
    }
}