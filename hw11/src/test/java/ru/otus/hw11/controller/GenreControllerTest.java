package ru.otus.hw11.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.hw11.dto.GenreDto;
import ru.otus.hw11.models.Genre;
import ru.otus.hw11.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private GenreRepository genreRepository;
    
    private List<Genre> genres = List.of(
            new Genre().setId(1L).setName("Роман"),
            new Genre().setId(2L).setName("Фэнтези"),
            new Genre().setId(3L).setName("Детектив")
    );

    @Test
    @DisplayName(" должен вернуть все жанры")
    void shouldReturnAllGenres() {
        Mockito.when(genreRepository.findAll()).thenReturn(Flux.fromStream(genres.stream()));
        
        var result = webTestClient
                .get().uri("/api/genre")
                .exchange()
                .expectStatus().isOk()
                .returnResult(GenreDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(genreDto ->
                        assertThat(genreDto).isNotNull()
                                .matches(a -> a.getId() == genres.get(0).getId()
                                        && a.getName().equals(genres.get(0).getName())
                                )
                )
                .assertNext(genreDto ->
                        assertThat(genreDto).isNotNull()
                                .matches(a -> a.getId() == genres.get(1).getId()
                                        && a.getName().equals(genres.get(1).getName())
                                )
                )
                .assertNext(genreDto ->
                        assertThat(genreDto).isNotNull()
                                .matches(a -> a.getId() == genres.get(2).getId()
                                        && a.getName().equals(genres.get(2).getName())
                                )
                )
                .verifyComplete();
    }
}