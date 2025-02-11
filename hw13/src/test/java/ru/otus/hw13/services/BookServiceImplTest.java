package ru.otus.hw13.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw13.dto.AuthorDto;
import ru.otus.hw13.dto.BookDto;
import ru.otus.hw13.dto.GenreDto;
import ru.otus.hw13.exceptions.EntityNotFoundException;

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
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldFindById() {
        long bookId = 1L;

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()))
                .matches(b -> Objects.nonNull(b.getGenre()))
                .matches(b -> Objects.nonNull(b.getAuthor()));
    }

    @Test
    @DisplayName(" должен бросить AccessDeniedException при попытке найти книгу по идентификатору")
    @WithMockUser(
            username = "vitya", password = "234"
    )
    void shouldThrowAccessDeniedExceptionWhenFindBydId() {
        long bookId = 1L;

        assertThatThrownBy(() -> bookService.findById(bookId))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @DisplayName(" должен найти все книги для суперпользователя")
    @WithMockUser(
            username = "super", password = "321"
    )
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
    @DisplayName(" должен найти все книги, для которых у пользователя есть права на чтение")
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldFindOnlyBooksThatCanBeRead() {
        List<BookDto> books = bookService.findAll();

        int expectedNumberOfBooks = 1;

        assertThat(books).hasSize(expectedNumberOfBooks)
                .allMatch(book -> StringUtils.hasText(book.getTitle()))
                .allMatch(book -> Objects.nonNull(book.getAuthor()))
                .allMatch(book -> Objects.nonNull(book.getGenre()))
                .allMatch(book -> !CollectionUtils.isEmpty(book.getComments()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" должен создать новую книгу")
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldInsert() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = 2L;

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));
        BookDto insertedBook = bookService.create(bookDto);

        Optional<BookDto> bookOptional = bookService.findById(insertedBook.getId());

        assertThat(bookOptional).isPresent().isNotNull().get()
                .matches(b -> StringUtils.hasText(b.getTitle()) && b.getTitle().equals(bookTitle))
                .matches(b -> Objects.nonNull(b.getGenre()) && b.getGenre().getId() == genreId)
                .matches(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().getId() == authorId);
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если автор не найден")
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldThrowAnEntityNotFoundExceptionIfAuthorDoesNotExist() {
        String bookTitle = "Игрок";
        long authorId = -2L;
        long genreId = 2L;

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        String expectedMessage = "Author with id %d not found".formatted(authorId);

        assertThatThrownBy(() -> bookService.create(bookDto))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если жанр не найден")
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldThrowAnEntityNotFoundExceptionIfGenreDoesNotExist() {
        String bookTitle = "Игрок";
        long authorId = 2L;
        long genreId = -2L;

        BookDto bookDto = new BookDto().setTitle(bookTitle)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        String expectedMessage = "Genre with id %d not found".formatted(genreId);

        assertThatThrownBy(() -> bookService.create(bookDto))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(
            username = "valera", password = "123"
    )
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
        BookDto updatedBook = bookService.update(bookDto);

        assertThat(updatedBook)
                .matches(ub -> ub.getId() == book.getId())
                .matches(ub -> !ub.getTitle().equals(book.getTitle()));
    }

    @Test
    @DisplayName(" должен бросить AccessDeniedException при обновлении книги пользователем, не имеющего прав на запись")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(
            username = "vitya", password = "password"
    )
    void shouldThrowAccessDeniedExceptionWhenUpdateBookWithoutWriteAccess() {
        long bookId = 1L;
        String title = "Гарри Топор и Медаль Феликса";
        long authorId = 1L;
        long genreId = 1L;

        BookDto bookDto = new BookDto()
                .setId(bookId)
                .setTitle(title)
                .setAuthor(new AuthorDto().setId(authorId))
                .setGenre(new GenreDto().setId(genreId));

        assertThatThrownBy(() -> bookService.update(bookDto))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(
            username = "valera", password = "123"
    )
    void shouldDeleteById() {
        long bookId = 1L;
        bookService.deleteById(bookId);

        Optional<BookDto> bookOptional = bookService.findById(bookId);

        assertThat(bookOptional).isEmpty();
    }

    @Test
    @DisplayName(" должен бросить AccessDeniedException при удалении книги пользователем, не имеющим прав на удаление")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(
            username = "vitya", password = "password"
    )
    void shouldThrowAccessDeniedExceptionWhenDeleteBookWithoutDeleteRights() {
        long bookId = 2L;

        assertThatThrownBy(() -> bookService.deleteById(bookId))
                .isInstanceOf(AccessDeniedException.class);
    }
}