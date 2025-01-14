package ru.otus.hw11.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import ru.otus.hw11.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName(" должен найти всех авторов")
    void shouldFindAll() {
        List<Author> authors = authorRepository.findAll().collectList().block();

        assertThat(authors).isNotNull()
                .allMatch(book -> StringUtils.hasText(book.getFullName()));
    }
}