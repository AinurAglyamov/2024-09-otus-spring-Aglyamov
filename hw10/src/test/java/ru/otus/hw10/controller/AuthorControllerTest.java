package ru.otus.hw10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw10.dto.AuthorDto;
import ru.otus.hw10.services.AuthorService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    private List<AuthorDto> authors = List.of(
            new AuthorDto().setId(1L).setFullName("Джоан Роулинг"),
            new AuthorDto().setId(2L).setFullName("Федор Достоевский"),
            new AuthorDto().setId(3L).setFullName("Артур Конан Дойл")
    );

    @Test
    @DisplayName(" должен вернуть всех авторов")
    void shouldReturnAllAuthors() throws Exception {
        when(authorService.findAll()).thenReturn(authors);
        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));
    }
}