package ru.otus.hw11.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw11.converters.BookConverter;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.models.Author;
import ru.otus.hw11.models.Book;
import ru.otus.hw11.models.Genre;
import ru.otus.hw11.repositories.BookRepositoryCustom;
import ru.otus.hw11.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private BookRepositoryCustom bookRepository;

    @Autowired
    private BookConverter bookConverter;

    @MockBean
    private GenreRepository genreRepository;

    private List<Author> authors = List.of(
            new Author().setId(1L).setFullName("Джоан Роулинг"),
            new Author().setId(2L).setFullName("Федор Достоевский")
    );

    private List<Genre> genres = List.of(
            new Genre().setId(1L).setName("Роман"),
            new Genre().setId(2L).setName("Фэнтези")
    );

    private List<Book> books = List.of(
            new Book().setId(1L).setTitle("Порри Гаттер и Каменный Философ").setAuthor(authors.get(0)).setGenre(genres.get(0)),
            new Book().setId(2L).setTitle("Братья Карамазовы").setAuthor(authors.get(0)).setGenre(genres.get(1))
    );

    @Test
    @DisplayName(" должен вернуть все книги")
    void shouldReturnBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(Flux.fromStream(books.stream()));

        var result = webTestClient
                .get().uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(bookDto ->
                        assertThat(bookDto).isNotNull()
                                .matches(b -> b.getId() == books.get(0).getId()
                                        && b.getTitle().equals(books.get(0).getTitle())
                                )
                )
                .assertNext(genreDto ->
                        assertThat(genreDto).isNotNull()
                                .matches(b -> b.getId() == books.get(1).getId()
                                        && b.getTitle().equals(books.get(1).getTitle())
                                )
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен вернуть книгу по идентификатору")
    void shouldReturnBookById() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Mono.just(books.get(0)));

        var result = webTestClient
                .get().uri("/api/book/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(bookDto ->
                        assertThat(bookDto).isNotNull()
                                .matches(b -> b.getId() == books.get(0).getId()
                                        && b.getTitle().equals(books.get(0).getTitle())
                                )
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен вернуть статус Not found, если книга не существует")
    void shouldReturnStatusNotFoundWhenBookDoesNotExist() {
        Mockito.when(bookRepository.findById(3L)).thenReturn(Mono.empty());

        webTestClient
                .get().uri("/api/book/3")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName(" должен создать книгу")
    void shouldCreateBook() {
        Book book = books.get(0);
        BookDto bookForCreate = bookConverter.bookToBookDto(book);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(books.get(0)));

        var result = webTestClient
                .post().uri("/api/book")
                .body(Mono.just(bookForCreate), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(bookDto ->
                        assertThat(bookDto).isNotNull()
                                .matches(b -> b.getId() == books.get(0).getId()
                                        && b.getTitle().equals(books.get(0).getTitle())
                                )
                )
                .verifyComplete();
    }

    @Test
    @DisplayName(" должен вернуть Внутреннюю ошибку сервера при ошибке создания книги")
    void shouldReturnInternalServerError() {
        Book book = books.get(0);
        BookDto bookForCreate = bookConverter.bookToBookDto(book);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(Mono.error(new RuntimeException()));

        webTestClient
                .post().uri("/api/book")
                .body(Mono.just(bookForCreate), BookDto.class)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName(" должен обновить книгу")
    void shouldUpdateBook() {
        Book book = books.get(0);
        BookDto bookForUpdate = bookConverter.bookToBookDto(book);
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(books.get(0)));
        Mockito.when(bookRepository.findById(bookForUpdate.getId())).thenReturn(Mono.just(books.get(0)));

        var result = webTestClient
                .put().uri("/api/book")
                .body(Mono.just(bookForUpdate), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(bookDto ->
                        assertThat(bookDto).isNotNull()
                                .matches(b -> b.getId() == books.get(0).getId()
                                        && b.getTitle().equals(books.get(0).getTitle())
                                )
                )
                .verifyComplete();

    }

    @Test
    @DisplayName(" должен удалить книгу")
    void shouldDeleteBook() {
        Mockito.when(bookRepository.deleteById(1L)).thenReturn(Mono.empty());

        var result = webTestClient
                .delete().uri("/api/book/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .verifyComplete();
    }
}