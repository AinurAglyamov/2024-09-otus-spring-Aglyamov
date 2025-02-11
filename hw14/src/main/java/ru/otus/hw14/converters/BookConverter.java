package ru.otus.hw14.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw14.models.mongo.MongoBook;
import ru.otus.hw14.models.relational.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {

    public Book mongoBookToBook(MongoBook book) {
        return new Book()
                .setTitle(book.getTitle())
                .setMongoAuthorId(book.getAuthor().getId())
                .setMongoGenreId(book.getGenre().getId());
    }
}
