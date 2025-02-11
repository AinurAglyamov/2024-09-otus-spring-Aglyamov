package ru.otus.hw14.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw14.models.mongo.MongoAuthor;
import ru.otus.hw14.models.relational.Author;

@Component
public class AuthorConverter {

    public Author mongoAuthorToRelationalAuthor(MongoAuthor author) {
        return new Author()
                .setFullName(author.getFullName())
                .setMongoId(author.getId());
    }
}
