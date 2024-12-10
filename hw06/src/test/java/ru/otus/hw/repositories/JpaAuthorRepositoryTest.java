package ru.otus.hw.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository authorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName(" должен загружать информацию об авторе по его id")
    void shouldFindAuthorById() {
        long authorId = 1L;

        Optional<Author> authorOptional = authorRepository.findById(authorId);
        Author author = testEntityManager.find(Author.class, authorId);

        assertThat(authorOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName(" должен загружать список всех авторов")
    void shouldFindAllAuthors() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var authors = authorRepository.findAll();
        int expectedNumberOfAuthors = 3;
        int expectedNumberOfQueries = 1;

        assertThat(authors).isNotNull().hasSize(expectedNumberOfAuthors)
                .allMatch(author -> StringUtils.hasText(author.getFullName()))
                .allMatch(author -> !CollectionUtils.isEmpty(author.getBooks()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(expectedNumberOfQueries);
    }
}