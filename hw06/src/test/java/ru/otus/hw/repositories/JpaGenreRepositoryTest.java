package ru.otus.hw.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import ru.otus.hw.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName(" должен загружать сущность жанра по id")
    void shouldFindGenreById() {
        long genreId = 1L;

        Optional<Genre> authorOptional = genreRepository.findById(genreId);
        Genre genre = em.find(Genre.class, genreId);

        assertThat(authorOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @DisplayName(" должен загружать список всех жанров")
    void shouldFindAllGenres() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var genres = genreRepository.findAll();
        int expectedNumberOfGenres = 3;
        int expectedNumberOfQueries = 1;

        assertThat(genres).isNotNull().hasSize(expectedNumberOfGenres)
                .allMatch(genre -> StringUtils.hasText(genre.getName()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(expectedNumberOfQueries);
    }
}