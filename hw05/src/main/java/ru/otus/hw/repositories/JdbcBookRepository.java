package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        var params = Map.of("id", id);

        List<Book> books = jdbc.query(
            "select " +
                    "b.id as book_id, " +
                    "b.title as title, " +
                    "a.id as author_id, " +
                    "a.full_name as full_name, " +
                    "g.id as genre_id, " +
                    "g.name as genre_name " +
                "from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id " +
                "where b.id = :id",
                params,
                new BookRowMapper()
        );

        if (books.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(books.get(0));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(
            "select " +
                    "b.id as book_id, " +
                    "b.title as title, " +
                    "a.id as author_id, " +
                    "a.full_name as full_name, " +
                    "g.id as genre_id, " +
                    "g.name as genre_name " +
                "from books b " +
                "left join authors a on a.id = b.author_id " +
                "left join genres g on g.id = b.genre_id",
                new BookRowMapper()
        );
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);

        jdbc.update(
                "delete from books where id = :id", params
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        jdbc.update("insert into books (title, author_id, genre_id) values (:title, :authorId, :genreId)",
                params, keyHolder);

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        var params = Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId(),
                "id", book.getId()
        );

        int updatedRows = jdbc.update(
                "update books set title = :title, author_id = :authorId, genre_id = :genreId where id = :id",
                params
        );

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("book_id");
            String title = rs.getString("title");

            long authorId = rs.getLong("author_id");
            String fullName = rs.getString("full_name");
            var author = new Author(authorId, fullName);

            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            var genre = new Genre(genreId, genreName);

            return new Book(id, title, author, genre);
        }
    }
}
