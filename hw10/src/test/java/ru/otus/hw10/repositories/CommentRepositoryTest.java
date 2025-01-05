package ru.otus.hw10.repositories;

import ru.otus.hw10.models.Author;
import ru.otus.hw10.models.Book;
import ru.otus.hw10.models.Comment;
import ru.otus.hw10.models.Genre;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName(" должен найти все комментарии по id книги")
    void shouldFindAllByBookId() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        long bookId = 2L;

        List<Comment> comments = commentRepository.findByBookId(bookId);
        int expectedNumberOfComments = 2;
        var expectedNumberOfQueries = 1;

        assertThat(comments).isNotNull().hasSize(expectedNumberOfComments)
                .allMatch(comment -> StringUtils.hasText(comment.getText()))
                .allMatch(comment -> Objects.nonNull(comment.getCommentDate()))
                .allMatch(comment -> Objects.nonNull(comment.getBook()));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(expectedNumberOfQueries);
    }

    @Test
    @DisplayName(" должен добавлять новый комментарий к книге")
    void shouldInsertComment() {
        Genre genre = new Genre().setName("Приключения");
        Author author = new Author().setFullName("Жюль Верн");
        Comment comment = new Comment().setText("Хорошая книга").setCommentDate(LocalDate.now());
        Book book = new Book()
                .setTitle("Дети капитана Гранта")
                .setGenre(genre)
                .setAuthor(author);

        comment.setBook(book);

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment.getId()).isGreaterThan(0);

        Comment actualComment = testEntityManager.find(Comment.class, savedComment.getId());

        assertThat(actualComment).isNotNull()
                .matches(c -> StringUtils.hasText(c.getText()))
                .matches(c -> Objects.nonNull(c.getCommentDate()));
    }

    @Test
    @DisplayName(" должен обновить существующий комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateComment() {
        long commentId = 1L;

        Comment comment = testEntityManager.find(Comment.class, commentId);
        comment.setText("Неплохо");

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment.getId()).isEqualTo(commentId);

        Comment actualComment = testEntityManager.find(Comment.class, savedComment.getId());

        assertThat(actualComment).isNotNull()
                .matches(c -> StringUtils.hasText(c.getText()))
                .matches(c -> Objects.nonNull(c.getCommentDate()));
    }

    @Test
    @DisplayName(" должен удалить комментарий по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentById() {
        long commentId = 1L;
        Comment comment = testEntityManager.find(Comment.class, commentId);

        assertThat(comment).isNotNull();
        testEntityManager.detach(comment);

        commentRepository.deleteById(commentId);
        Comment deletedComment = testEntityManager.find(Comment.class, commentId);

        assertThat(deletedComment).isNull();
    }
}