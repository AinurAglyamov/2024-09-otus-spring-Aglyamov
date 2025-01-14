package ru.otus.hw11.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.models.Book;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public BookDto bookToBookDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setGenre(Optional.ofNullable(book.getGenre()).map(genreConverter::genreToGenreDto).orElse(null))
                .setAuthor(Optional.ofNullable(book.getAuthor()).map(authorConverter::authorToAuthorDto).orElse(null));
    }
}
