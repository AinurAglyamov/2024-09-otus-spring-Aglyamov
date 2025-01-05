package ru.otus.hw10.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw10.dto.BookDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PagesController.class)
class PagesControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName(" должен отображать страницу со списками книг")
    void shouldRenderBooksPageWithCorrectView() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("books"));
    }

    @Test
    @DisplayName(" должен отображать страницу с формой создания книги")
    void shouldRenderCreationPageWithCorrectViewAndModelAttributes() throws Exception {
        mvc.perform(get("/create"))
                .andExpect(view().name("create"));
    }

    @Test
    @DisplayName(" должен отображать страницу с формой редактирования книги")
    void shouldRenderEditingPageWithCorrectViewAndModelAttributes() throws Exception {
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("edit"));
    }
}