package ru.otus.hw08.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw08.dto.GenreDto;
import ru.otus.hw08.models.Genre;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }

    public GenreDto genreToGenreDto(Genre genre) {
        return new GenreDto()
                .setId(genre.getId())
                .setName(genre.getName());
    }
}
