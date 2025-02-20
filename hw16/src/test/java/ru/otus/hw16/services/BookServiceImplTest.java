package ru.otus.hw16.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw16.dto.AuthorDto;
import ru.otus.hw16.dto.BookDto;
import ru.otus.hw16.dto.GenreDto;
import ru.otus.hw16.exceptions.EntityNotFoundException;

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

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));
        BookDto insertedBook = bookService.save(bookDto);

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

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        String expectedMessage = "Author with id %d not found".formatted(authorId);

        assertThatThrownBy(() -> bookService.save(bookDto))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если жанр не найден")
    void shouldThrowAnEntityNotFoundExceptionIfGenreDoesNotExist() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = -2L;

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        String expectedMessage = "Genre with id %d not found".formatted(genreId);

        assertThatThrownBy(() -> bookService.save(bookDto))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdate() {
        long bookId = 1L;
        String title = "Гарри Топор и Медаль Феликса";
        long authorId = 1L;
        long genreId = 1L;

        BookDto bookDto = new BookDto()
                .setId(bookId)
                .setTitle(title)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        BookDto book = bookService.findById(bookId).get();
        BookDto updatedBook = bookService.save(bookDto);

        assertThat(updatedBook)
                .matches(ub -> ub.getId() == book.getId())
                .matches(ub -> !ub.getTitle().equals(book.getTitle()));
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        long bookId = 1L;
        bookService.deleteById(bookId);

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isEmpty();
    }
}