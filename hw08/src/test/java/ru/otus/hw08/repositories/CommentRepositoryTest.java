package ru.otus.hw08.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;
import ru.otus.hw08.models.Comment;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName(" должен добавлять новый комментарий")
    void shouldInsertComment() {
        Comment comment = new Comment().setText("Хорошая книга");

        Comment savedComment = commentRepository.save(comment);
        Comment actualComment = mongoTemplate.findById(savedComment.getId(), Comment.class);

        assertThat(actualComment).isNotNull();

        assertThat(savedComment).isNotNull()
                .matches(c -> c.getId().equals(actualComment.getId()))
                .matches(c -> StringUtils.hasText(c.getText()));
    }

    @Test
    @DisplayName(" должен обновить существующий комментарий")
    void shouldUpdateComment() {
        BigInteger commentId = new BigInteger("1");

        Comment comment = mongoTemplate.findById(commentId, Comment.class);
        assertThat(comment).isNotNull();

        comment.setText("Неплохо");

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment.getId()).isEqualTo(commentId);

        Comment actualComment = mongoTemplate.findById(savedComment.getId(), Comment.class);
        assertThat(actualComment).isNotNull();

        assertThat(savedComment).isNotNull()
                .matches(c -> c.getId().equals(actualComment.getId()))
                .matches(c -> c.getText().equals(actualComment.getText()));
    }

    @Test
    @DisplayName(" должен удалить комментарий по id")
    void shouldDeleteCommentById() {
        BigInteger commentId = new BigInteger("1");
        Comment comment = mongoTemplate.findById(commentId, Comment.class);

        assertThat(comment).isNotNull();

        commentRepository.deleteById(commentId);
        Comment deletedComment = mongoTemplate.findById(commentId, Comment.class);

        assertThat(deletedComment).isNull();
    }
}