package ru.otus.hw16.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw16.dto.CommentDto;
import ru.otus.hw16.models.Comment;

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
                .setCommentDate(comment.getCommentDate());
    }
}
