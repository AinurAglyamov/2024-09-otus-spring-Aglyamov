package ru.otus.hw08.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;
import ru.otus.hw08.dto.CommentDto;
import ru.otus.hw08.exceptions.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName(" должен найти все комментарии по id книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllByBookId() {
        var bookId = new BigInteger("1");

        List<CommentDto> comments = commentService.findAllByBookId(bookId);

        int expectedNumberOfComments = 2;

        assertThat(comments).hasSize(expectedNumberOfComments)
                .allMatch(comment -> StringUtils.hasText(comment.getText()));
    }

    @Test
    @DisplayName(" должен найти комментарий по id")
    void shouldFindById() {
        var commentId = new BigInteger("1");

        Optional<CommentDto> commentOptional = commentService.findById(commentId);

        assertThat(commentOptional).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()));
    }

    @Test
    @DisplayName(" должен добавлять новый комментарий к книге")
    void shouldInsertComment() {
        var bookId = new BigInteger("1");
        String text = "Не люблю Гарри Поттера";

        CommentDto insertedComment = commentService.insert(text, bookId);

        assertThat(insertedComment).isNotNull()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если книга не найдена")
    void shouldThrowAnEntityNotFoundExceptionIfBookDoesNotExist() {
        var bookId = new BigInteger("-1");
        String text = "Не люблю Гарри Поттера";

        String expectedMessage = "Book with id %d not found".formatted(bookId);

        assertThatThrownBy(() -> commentService.insert(text, bookId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить комментарий")
    void shouldUpdateComment() {
        var commentId = new BigInteger("1");
        String text = "Поменял свое мнение";

        CommentDto updatedComment = commentService.update(commentId, text);

        Optional<CommentDto> comment = commentService.findById(updatedComment.getId());
        assertThat(comment).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если комментарий не найден")
    void shouldThrowAnEntityNotFoundExceptionIfCommentDoesNotExist() {
        var commentId = new BigInteger("-1");
        String text = "Не люблю Гарри Поттера";

        String expectedMessage = "Comment with id %d not found".formatted(commentId);

        assertThatThrownBy(() -> commentService.update(commentId, text))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен удалить комментарий по id")
    void shouldDeleteCommentById() {
        var commentId = new BigInteger("1");
        commentService.deleteById(commentId);

        Optional<CommentDto> comment = commentService.findById(commentId);

        assertThat(comment).isEmpty();
    }
}