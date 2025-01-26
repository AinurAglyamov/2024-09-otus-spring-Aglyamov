package ru.otus.hw12.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw12.dto.AuthorDto;
import ru.otus.hw12.dto.BookDto;
import ru.otus.hw12.dto.GenreDto;
import ru.otus.hw12.security.SecurityConfiguration;
import ru.otus.hw12.services.AuthorService;
import ru.otus.hw12.services.BookService;
import ru.otus.hw12.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

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

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен отображать страницу со списками книг")
    void shouldRenderBooksPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mvc.perform(get("/"))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", books));
    }

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен отображать страницу с формой создания книги")
    void shouldRenderCreationPageWithCorrectViewAndModelAttributes() throws Exception {
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/create"))
                .andExpect(view().name("create"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres));
    }

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен отображать страницу с формой редактирования книги")
    void shouldRenderEditingPageWithCorrectViewAndModelAttributes() throws Exception {
        BookDto expectedBook = books.get(0);
        when(bookService.findById(1L)).thenReturn(Optional.of(books.get(0)));
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBook));
    }

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен отображать страницу с формой редактирования книги")
    void shouldRenderErrorPageWhenBookNotFound() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("customError"));
    }

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен создавать книгу и перенаправлять в корневую страницу")
    void shouldCreateBookAndRedirectToContextPath() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(books.get(0)));
        mvc.perform(post("/create").param("name", "Убойная сила (Книга)"))
                .andExpect(view().name("redirect:/"));
        verify(bookService, times(1)).save(any(BookDto.class));
    }

    @WithMockUser(
            username = "testUser"
    )
    @Test
    @DisplayName(" должен обновлять книгу и перенаправлять в корневую страницу")
    void shouldUpdateBookAndRedirectToContextPath() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(books.get(0)));
        mvc.perform(post("/edit").param("id", "3").param("name", "11/22/63"))
                .andExpect(view().name("redirect:/"));
        verify(bookService, times(1)).save(any(BookDto.class));
    }
}