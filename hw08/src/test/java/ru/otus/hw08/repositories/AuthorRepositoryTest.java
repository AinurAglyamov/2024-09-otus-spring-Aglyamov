package ru.otus.hw08.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw08.models.Author;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName(" должен загружать информацию об авторе по его id")
    void shouldFindAuthorById() {
        BigInteger authorId = new BigInteger("1");

        Optional<Author> authorOptional = authorRepository.findById(authorId);

        Author author = mongoTemplate.findById(authorId, Author.class);;

        assertThat(authorOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName(" должен загружать список всех авторов")
    void shouldFindAllAuthors() {
        var authors = authorRepository.findAll();
        int expectedNumberOfAuthors = 3;

        assertThat(authors).isNotNull().hasSize(expectedNumberOfAuthors)
                .allMatch(author -> StringUtils.hasText(author.getFullName()))
                .allMatch(author -> !CollectionUtils.isEmpty(author.getBooks()));
    }
}