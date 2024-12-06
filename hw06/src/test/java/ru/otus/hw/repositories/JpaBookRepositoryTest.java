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
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaBookRepository.class)
class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName(" должен загружать сущность книги по id")
    void shouldFindBookById() {
        long bookId = 2L;

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Book book = em.find(Book.class, bookId);

        assertThat(bookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName(" должен загружать список всех книг")
    void shouldFindAllBooks() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var books = bookRepository.findAll();
        var expectedNumberOfBooks = 3;
        var expectedNumberOfQueries = 1;

        assertThat(books).isNotNull().hasSize(expectedNumberOfBooks)
                .allMatch(book -> StringUtils.hasText(book.getTitle()))
                .allMatch(book -> Objects.nonNull(book.getAuthor()))
                .allMatch(book -> Objects.nonNull(book.getGenre()))
                .allMatch(book -> !CollectionUtils.isEmpty(book.getComments()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(expectedNumberOfQueries);
    }

    @Test
    @DisplayName(" должен создать новую книгу")
    void shouldInsertBook() {
        Genre genre = new Genre().setName("Приключения");
        Author author = new Author().setFullName("Жюль Верн");

        Comment comment = new Comment().setText("Блестящая книга");
        List<Comment> comments = List.of(comment);

        Book book = new Book()
                .setTitle("Дети капитана Гранта")
                .setGenre(genre)
                .setAuthor(author)
                .setComments(comments);

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, savedBook.getId());
        assertThat(actualBook).isNotNull()
                .matches(b -> StringUtils.hasText(b.getTitle()))
                .matches(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().getId() > 0)
                .matches(b -> Objects.nonNull(b.getGenre()) && b.getGenre().getId() > 0)
                .matches(b -> !CollectionUtils.isEmpty(b.getComments()));
    }

    @Test
    @DisplayName(" должен обновить существующую книгу")
    void shouldUpdateBook() {
        long bookId = 1L;

        Book book = em.find(Book.class, bookId);
        book.setTitle("Гарик Повар и Кубок ЛЧ");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isEqualTo(bookId);

        Book actualBook = em.find(Book.class, savedBook.getId());
        assertThat(actualBook).isNotNull()
                .matches(b -> StringUtils.hasText(b.getTitle()) && b.getTitle().equals(book.getTitle()))
                .matches(b -> Objects.nonNull(b.getAuthor()) && b.getAuthor().getId() > 0)
                .matches(b -> Objects.nonNull(b.getGenre()) && b.getGenre().getId() > 0)
                .matches(b -> !CollectionUtils.isEmpty(b.getComments()));
    }

    @Test
    @DisplayName(" должен удалить книгу по id")
    void shouldDeleteBookById() {
        long bookId = 1L;
        Book book = em.find(Book.class, bookId);

        assertThat(book).isNotNull();
        em.detach(book);

        bookRepository.deleteById(bookId);
        Book deletedBook = em.find(Book.class, bookId);

        assertThat(deletedBook).isNull();
    }
}