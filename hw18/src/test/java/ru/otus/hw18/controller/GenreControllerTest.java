package ru.otus.hw18.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw18.dto.GenreDto;
import ru.otus.hw18.services.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    private List<GenreDto> genres = List.of(
            new GenreDto().setId(1L).setName("Роман"),
            new GenreDto().setId(2L).setName("Фэнтези"),
            new GenreDto().setId(3L).setName("Детектив")
    );

    @Test
    @DisplayName("должен вернуть список всех жанров")
    void shouldReturnGenres() throws Exception {
        when(genreService.findAll()).thenReturn(genres);
        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));
    }
}