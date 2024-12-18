package ru.otus.hw08.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw08.converters.BookConverter;
import ru.otus.hw08.dto.BookDto;
import ru.otus.hw08.exceptions.EntityNotFoundException;
import ru.otus.hw08.models.Book;
import ru.otus.hw08.repositories.AuthorRepository;
import ru.otus.hw08.repositories.BookRepository;
import ru.otus.hw08.repositories.GenreRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    @Override
    public Optional<BookDto> findById(BigInteger id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToBookDto);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::bookToBookDto).toList();
    }

    @Override
    public BookDto insert(String title, BigInteger authorId, BigInteger genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);

        var insertedBook = bookRepository.save(book);
        return bookConverter.bookToBookDto(insertedBook);
    }

    @Override
    public BookDto update(BigInteger id, String title, BigInteger authorId, BigInteger genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);

        Book updatedBook = bookRepository.save(book);
        return bookConverter.bookToBookDto(updatedBook);
    }

    @Override
    public void deleteById(BigInteger id) {
        bookRepository.deleteById(id);
    }
}
