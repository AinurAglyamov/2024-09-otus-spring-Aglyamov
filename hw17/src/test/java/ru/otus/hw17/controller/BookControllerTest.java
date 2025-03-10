package ru.otus.hw17.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw17.dto.AuthorDto;
import ru.otus.hw17.dto.BookDto;
import ru.otus.hw17.dto.GenreDto;
import ru.otus.hw17.services.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private BookService bookService;

    private List<AuthorDto> authors = List.of(
            new AuthorDto().setId(1L).setFullName("Джоан Роулинг"),
            new AuthorDto().setId(2L).setFullName("Федор Достоевский"),
            new AuthorDto().setId(3L).setFullName("Артур Конан Дойл")
    );

    private List<GenreDto> genres = List.of(
            new GenreDto().setId(1L).setName("Роман"),
            new GenreDto().setId(2L).setName("Фэнтези"),
            new GenreDto().setId(3L).setName("Детектив")
    );

    private List<BookDto> books = List.of(
            new BookDto().setId(1L).setAuthor(authors.get(0)).setGenre(genres.get(0)),
            new BookDto().setId(2L).setAuthor(authors.get(0)).setGenre(genres.get(1)),
            new BookDto().setId(3L).setAuthor(authors.get(0)).setGenre(genres.get(2))
    );

    @Test
    @DisplayName(" должен вернуть все книги")
    void shouldReturnBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    @DisplayName(" должен вернуть книгу по идентификатору")
    void shouldReturnBookById() throws Exception {
        BookDto book = books.get(0);
        when(bookService.findById(book.getId())).thenReturn(Optional.of(book));
        mvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books.get(0))));
    }

    @Test
    @DisplayName(" должен вернуть статус Not found, если книга не существует")
    void shouldReturnStatusNotFoundWhenBookDoesNotExist() throws Exception {
        BookDto book = books.get(0);
        when(bookService.findById(book.getId())).thenReturn(Optional.of(book));
        mvc.perform(get("/api/books/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(" должен создать книгу")
    void shouldCreateBook() throws Exception {
        BookDto book = books.get(0);
        when(bookService.save(book)).thenReturn(book);
        mvc.perform(post("/api/books").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books.get(0))));
    }

    @Test
    @DisplayName(" должен вернуть Внутреннюю ошибку сервера при ошибке создания книги")
    void shouldReturnInternalServerError() throws Exception {
        BookDto book = books.get(0);
        when(bookService.save(book)).thenThrow(RuntimeException.class);

        mvc.perform(post("/api/books").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(book)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName(" должен обновить книгу")
    void shouldUpdateBook() throws Exception {
        BookDto book = books.get(0);
        when(bookService.save(book)).thenReturn(book);
        mvc.perform(put("/api/books").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books.get(0))));
    }

    @Test
    @DisplayName(" должен удалить книгу")
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/books?id=1"))
                .andExpect(status().isOk());
    }
}