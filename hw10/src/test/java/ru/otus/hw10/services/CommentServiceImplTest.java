package ru.otus.hw10.services;

import ru.otus.hw10.dto.CommentDto;
import ru.otus.hw10.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName(" должен найти все комментарии по id книги")
    void shouldFindAllByBookId() {
        long bookId = 1L;

        List<CommentDto> comments = commentService.findAllByBookId(bookId);

        int expectedNumberOfComments = 2;

        assertThat(comments).hasSize(expectedNumberOfComments)
                .allMatch(comment -> StringUtils.hasText(comment.getText()))
                .allMatch(comment -> Objects.nonNull(comment.getCommentDate()));
    }

    @Test
    @DisplayName(" должен найти комментарий по id")
    void shouldFindById() {
        long commentId = 1L;

        Optional<CommentDto> commentOptional = commentService.findById(commentId);

        assertThat(commentOptional).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()))
                .matches(c -> Objects.nonNull(c.getCommentDate()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" должен добавлять новый комментарий к книге")
    void shouldInsertComment() {
        long bookId = 1L;
        String text = "Не люблю Гарри Поттера";

        CommentDto insertedComment = commentService.insert(text, bookId);

        Optional<CommentDto> comment = commentService.findById(insertedComment.getId());

        assertThat(comment).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если книга не найдена")
    void shouldThrowAnEntityNotFoundExceptionIfBookDoesNotExist() {
        long bookId = -1L;
        String text = "Не люблю Гарри Поттера";

        String expectedMessage = "Book with id %d not found".formatted(bookId);

        assertThatThrownBy(() -> commentService.insert(text, bookId))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен обновить комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateComment() {
        long commentId = 1L;
        String text = "Поменял свое мнение";

        CommentDto updatedComment = commentService.update(commentId, text);

        Optional<CommentDto> comment = commentService.findById(updatedComment.getId());
        assertThat(comment).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text));
    }

    @Test
    @DisplayName(" должен бросать EntityNotFoundException если комментарий не найден")
    void shouldThrowAnEntityNotFoundExceptionIfCommentDoesNotExist() {
        long commentId = -1L;
        String text = "Не люблю Гарри Поттера";

        String expectedMessage = "Comment with id %d not found".formatted(commentId);

        assertThatThrownBy(() -> commentService.update(commentId, text))
                .isInstanceOf(EntityNotFoundException.class)
                .matches(e -> e.getMessage().equals(expectedMessage));
    }

    @Test
    @DisplayName(" должен удалить комментарий по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentById() {
        long commentId = 1L;
        commentService.deleteById(commentId);

        Optional<CommentDto> comment = commentService.findById(commentId);

        assertThat(comment).isEmpty();
    }
}