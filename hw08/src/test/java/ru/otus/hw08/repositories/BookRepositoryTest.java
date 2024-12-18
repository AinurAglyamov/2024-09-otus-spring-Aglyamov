package ru.otus.hw08.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.hw08.models.Book;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName(" должен загружать сущность книги по id")
    void shouldFindBookById() {
        BigInteger bookId = new BigInteger("2");

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Book book = mongoTemplate.findById(bookId, Book.class);

        assertThat(bookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName(" должен загружать список всех книг")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllBooks() {
        var books = bookRepository.findAll();
        var expectedNumberOfBooks = 3;

        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(book -> StringUtils.hasText(book.getTitle()))
                .allMatch(book -> Objects.nonNull(book.getAuthor()))
                .allMatch(book -> Objects.nonNull(book.getGenre()))
                .allMatch(book -> !CollectionUtils.isEmpty(book.getComments()));
    }

    @Test
    @DisplayName(" должен создать новую книгу")
    void shouldInsertBook() {
        Book book = new Book()
                .setTitle("Дети капитана Гранта");

        Book savedBook = bookRepository.save(book);
        Book actualBook = mongoTemplate.findById(savedBook.getId(), Book.class);

        assertThat(savedBook).isNotNull()
                .matches(b -> b.getId().equals(actualBook.getId()))
                .matches(b -> StringUtils.hasText(b.getTitle()));
    }

    @Test
    @DisplayName(" должен обновить существующую книгу")
    void shouldUpdateBook() {
        BigInteger bookId = new BigInteger("1");

        Book book = mongoTemplate.findById(bookId, Book.class);
        assertThat(book).isNotNull();
        book.setTitle("Гарик Повар и Кубок ЛЧ");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isEqualTo(bookId);

        Book actualBook = mongoTemplate.findById(savedBook.getId(), Book.class);

        assertThat(actualBook).isNotNull()
                .matches(b -> StringUtils.hasText(b.getTitle()) && b.getTitle().equals(book.getTitle()))
                .matches(b -> Objects.nonNull(b.getAuthor()))
                .matches(b -> Objects.nonNull(b.getGenre()))
                .matches(b -> !CollectionUtils.isEmpty(b.getComments()));
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    void shouldDeleteBookById() {
        BigInteger bookId = new BigInteger("1");
        Book book = mongoTemplate.findById(bookId, Book.class);

        assertThat(book).isNotNull();

        bookRepository.deleteById(bookId);
        Book deletedBook = mongoTemplate.findById(bookId, Book.class);

        assertThat(deletedBook).isNull();
    }
}