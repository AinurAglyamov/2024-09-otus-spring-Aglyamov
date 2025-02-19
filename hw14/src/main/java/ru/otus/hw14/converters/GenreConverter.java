package ru.otus.hw14.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw14.models.mongo.MongoGenre;
import ru.otus.hw14.models.relational.Genre;

@Component
public class GenreConverter {

    public Genre mongoGenreToGenre(MongoGenre genre) {
        return new Genre()
                .setName(genre.getName())
                .setMongoId(genre.getId());
    }
}
