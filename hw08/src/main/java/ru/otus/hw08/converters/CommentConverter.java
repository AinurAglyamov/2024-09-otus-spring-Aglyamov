package ru.otus.hw08.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw08.dto.CommentDto;
import ru.otus.hw08.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    public String commentToString(CommentDto comment) {
        return "Id: %d, Text: %s".formatted(
                comment.getId(),
                comment.getText()
        );
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto()
                .setId(comment.getId())
                .setText(comment.getText())
                .setBook(comment.getBook());
    }
}
