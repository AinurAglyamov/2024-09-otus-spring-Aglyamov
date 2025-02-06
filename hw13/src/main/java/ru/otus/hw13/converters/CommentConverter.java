package ru.otus.hw13.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw13.dto.CommentDto;
import ru.otus.hw13.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    public String commentToString(CommentDto comment) {
        return "Id: %d, Text: %s, Date: %s".formatted(
                comment.getId(),
                comment.getText(),
                comment.getCommentDate()
        );
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto()
                .setId(comment.getId())
                .setText(comment.getText())
                .setBook(comment.getBook())
                .setCommentDate(comment.getCommentDate());
    }
}
