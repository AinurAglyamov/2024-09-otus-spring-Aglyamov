package ru.otus.hw08.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;
import ru.otus.hw08.models.Genre;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName(" должен загружать сущность жанра по id")
    void shouldFindGenreById() {
        BigInteger genreId = new BigInteger("1");

        Optional<Genre> authorOptional = genreRepository.findById(genreId);
        Genre genre = mongoTemplate.findById(genreId, Genre.class);

        assertThat(authorOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @DisplayName(" должен загружать список всех жанров")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllGenres() {
        var genres = genreRepository.findAll();
        int expectedNumberOfGenres = 3;

        assertThat(genres).isNotNull().hasSize(expectedNumberOfGenres)
                .allMatch(genre -> StringUtils.hasText(genre.getName()));
    }
}