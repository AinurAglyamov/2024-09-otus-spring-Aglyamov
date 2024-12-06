package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.StringUtils;
import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName(" должен найти все комментарии по id книги")
    void findAllByBookId() {
        long bookId = 1L;

        List<CommentDto> comments = commentService.findAllByBookId(bookId);

        int expectedNumberOfComments = 2;

        assertThat(comments).hasSize(expectedNumberOfComments)
                .allMatch(comment -> StringUtils.hasText(comment.getText()))
                .allMatch(comment -> Objects.nonNull(comment.getCommentDate()))
                .allMatch(comment -> Objects.nonNull(comment.getBook()));
    }

    @Test
    @DisplayName(" должен найти комментарий по id")
    void findById() {
        long commentId = 1L;

        Optional<CommentDto> commentOptional = commentService.findById(commentId);

        assertThat(commentOptional).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()))
                .matches(c -> Objects.nonNull(c.getCommentDate()))
                .matches(c -> Objects.nonNull(c.getBook()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" должен добавлять новый комментарий к книге")
    void insert() {
        long bookId = 1L;
        String text = "Не люблю Гарри Поттера";

        CommentDto insertedComment = commentService.insert(text, bookId);

        Optional<CommentDto> comment = commentService.findById(insertedComment.getId());

        assertThat(comment).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text))
                .matches(c -> Objects.nonNull(c.getBook()) && c.getBook().getId() == bookId);
    }

    @Test
    @DisplayName(" должен обновить комментарий")
    void update() {
        long commentId = 1L;
        String text = "Поменял свое мнение";

        CommentDto updatedComment = commentService.update(commentId, text);

        Optional<CommentDto> comment = commentService.findById(updatedComment.getId());
        assertThat(comment).isPresent().isNotNull().get()
                .matches(c -> StringUtils.hasText(c.getText()) && c.getText().equals(text));
    }

    @Test
    @DisplayName(" должен удалить комментарий по id")
    void deleteById() {
        long commentId = 1L;
        commentService.deleteById(commentId);

        Optional<CommentDto> comment = commentService.findById(commentId);

        assertThat(comment).isEmpty();
    }
}