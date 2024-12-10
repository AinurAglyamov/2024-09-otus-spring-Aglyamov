package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;

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
        long bookId = 1L;

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()))
                .matches(b -> Objects.nonNull(b.getGenre()))
                .matches(b -> Objects.nonNull(b.getAuthor()));
    }

    @Test
    @DisplayName(" должен найти все книги")
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" должен создать новую книгу")
    void shouldInsert() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = 2L;
        BookDto insertedBook = bookService.insert(bookTitle, authorId, genreId);

        Optional<BookDto> bookOptional = bookService.findById(insertedBook.getId());

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()) && b.getTitle().equals(bookTitle))
                .matches(b -> Objects.nonNull(b.getGenre()) && b.getGenre().getId() == genreId)
                .matches(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().getId() == authorId);
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если автор не найден")
    void shouldThrowAnEntityNotFoundExceptionIfAuthorDoesNotExist() {
        String bookTitle = "Игрок";
        long authorId = -2L;
        long genreId = 2L;

        String expectedMessage = "Author with id %d not found".formatted(authorId);

        assertThatThrownBy(() -> bookService.insert(bookTitle, authorId, genreId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если жанр не найден")
    void shouldThrowAnEntityNotFoundExceptionIfGenreDoesNotExist() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = -2L;

        String expectedMessage = "Genre with id %d not found".formatted(genreId);

        assertThatThrownBy(() -> bookService.insert(bookTitle, authorId, genreId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить книгу")
    void shouldUpdate() {
        long bookId = 1L;
        String title = "Гарри Харламов и Медаль Феликса";
        long authorId = 1L;
        long genreId = 1L;

        BookDto book = bookService.findById(bookId).get();
        BookDto updatedBook = bookService.update(bookId, title, authorId, genreId);

        assertThat(updatedBook)
                .matches(ub -> ub.getId() == book.getId())
                .matches(ub -> !ub.getTitle().equals(book.getTitle()));
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    void shouldDeleteById() {
        long bookId = 1L;
        bookService.deleteById(bookId);

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isEmpty();
    }
}