package ru.otus.hw11.repositories.impl;

import io.r2dbc.spi.Readable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.models.Author;
import ru.otus.hw11.models.Book;
import ru.otus.hw11.models.Genre;
import ru.otus.hw11.repositories.BookRepository;
import ru.otus.hw11.repositories.BookRepositoryCustom;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private static final String FIND_BY_ID = """
            select
            b.id as book_id, 
            b.title as book_title, 
            a.id as author_id, 
            a.full_name as author_full_name, 
            g.id as genre_id, 
            g.name as genre_name
            from books b
            join authors a on b.author_id = a.id
            join genres g on b.genre_id = g.id
            where b.id = $1
            """;

    private static final String FIND_ALL = """
            select 
            b.id as book_id, 
            b.title as book_title, 
            a.id as author_id, 
            a.full_name as author_full_name, 
            g.id as genre_id, 
            g.name as genre_name
            from books b
            join authors a on b.author_id = a.id
            join genres g on b.genre_id = g.id
            """;

    private final R2dbcEntityTemplate template;

    private final BookRepository bookRepository;

    @Override
    public Mono<Book> findById(Long id) {
        return template.getDatabaseClient().inConnection(connection ->
                Mono.from(connection.createStatement(FIND_BY_ID).bind("$1", id)
                        .execute())
                .flatMap(result -> Mono.from(result.map(this::mapToBook))));
    }

    @Override
    public Flux<Book> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(FIND_ALL)
                                .execute())
                        .flatMap(result -> result.map(this::mapToBook)));
    }

    @Override
    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return bookRepository.deleteById(id);
    }

    private Book mapToBook(Readable selectedRecord) {
        Long bookId = selectedRecord.get("book_id", Long.class);
        String bookTitle = selectedRecord.get("book_title", String.class);
        Long authorId = selectedRecord.get("author_id", Long.class);
        String authorFullName = selectedRecord.get("author_full_name", String.class);
        Long genreId = selectedRecord.get("genre_id", Long.class);
        String genreName = selectedRecord.get("genre_name", String.class);

        Author author = new Author()
                .setId(Optional.ofNullable(authorId).orElseThrow())
                .setFullName(authorFullName);

        Genre genre = new Genre()
                .setId(Optional.ofNullable(genreId).orElseThrow())
                .setName(genreName);

        return new Book()
                .setId(Optional.ofNullable(bookId).orElseThrow())
                .setTitle(bookTitle)
                .setAuthorId(authorId)
                .setGenreId(genreId)
                .setAuthor(author)
                .setGenre(genre);
    }
}
