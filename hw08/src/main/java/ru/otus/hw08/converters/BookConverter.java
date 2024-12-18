package ru.otus.hw08.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw08.dto.BookDto;
import ru.otus.hw08.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public String bookToString(BookDto book) {
        return "Id: %d, title: %s, author: {%s}, genre: [%s], comments: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre()),
                book.getComments().stream()
                        .map(commentConverter::commentToString)
                        .collect(Collectors.joining(", "))
        );
    }

    public BookDto bookToBookDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setGenre(genreConverter.genreToGenreDto(book.getGenre()))
                .setAuthor(authorConverter.authorToAuthorDto(book.getAuthor()))
                .setComments(Optional.ofNullable(book.getComments())
                        .map(comments -> comments.stream().map(commentConverter::commentToCommentDto).toList())
                        .orElse(List.of())
                );
    }
}
