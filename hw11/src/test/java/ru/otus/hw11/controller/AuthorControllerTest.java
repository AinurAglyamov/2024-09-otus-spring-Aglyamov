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
import ru.otus.hw11.dto.AuthorDto;
import ru.otus.hw11.models.Author;
import ru.otus.hw11.repositories.AuthorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    private List<Author> authors = List.of(
            new Author().setId(1L).setFullName("Джоан Роулинг"),
            new Author().setId(2L).setFullName("Федор Достоевский"),
            new Author().setId(3L).setFullName("Артур Конан Дойл")
    );

    @Test
    @DisplayName(" должен вернуть всех авторов")
    void shouldReturnAllAuthors() {
        Mockito.when(authorRepository.findAll()).thenReturn(Flux.fromStream(authors.stream()));

        var result = webTestClient
                .get().uri("/api/author")
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();

        StepVerifier.create(result)
                .assertNext(authorDto ->
                        assertThat(authorDto).isNotNull()
                                .matches(a -> a.getId() == authors.get(0).getId()
                                        && a.getFullName().equals(authors.get(0).getFullName())
                                )
                )
                .assertNext(authorDto ->
                        assertThat(authorDto).isNotNull()
                                .matches(a -> a.getId() == authors.get(1).getId()
                                        && a.getFullName().equals(authors.get(1).getFullName())
                                )
                )
                .assertNext(authorDto ->
                        assertThat(authorDto).isNotNull()
                                .matches(a -> a.getId() == authors.get(2).getId()
                                        && a.getFullName().equals(authors.get(2).getFullName())
                                )
                )
                .verifyComplete();
    }
}