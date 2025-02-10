package ru.otus.hw12.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw12.converters.BookConverter;
import ru.otus.hw12.dto.AuthorDto;
import ru.otus.hw12.dto.BookDto;
import ru.otus.hw12.dto.GenreDto;
import ru.otus.hw12.exceptions.EntityNotFoundException;
import ru.otus.hw12.models.Author;
import ru.otus.hw12.models.Book;
import ru.otus.hw12.models.Genre;
import ru.otus.hw12.repositories.AuthorRepository;
import ru.otus.hw12.repositories.BookRepository;
import ru.otus.hw12.repositories.GenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToBookDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::bookToBookDto).toList();
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        Book book;
        if (bookDto.getId() != 0L) {
            long id = bookDto.getId();
            book = bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        } else {
            book = new Book();
        }

        AuthorDto authorDto = bookDto.getAuthor();
        if (Objects.nonNull(authorDto)) {
            book.setAuthor(loadAuthor(authorDto.getId()));
        }

        GenreDto genreDto = bookDto.getGenre();
        if (Objects.nonNull(genreDto)) {
            book.setGenre(loadGenre(genreDto.getId()));
        }

        book.setTitle(bookDto.getTitle());

        var insertedBook = bookRepository.save(book);
        return bookConverter.bookToBookDto(insertedBook);
    }

    private Author loadAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
    }

    private Genre loadGenre(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }


}
