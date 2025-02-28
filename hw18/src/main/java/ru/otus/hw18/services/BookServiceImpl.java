package ru.otus.hw18.services;

import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw18.converters.BookConverter;
import ru.otus.hw18.dto.AuthorDto;
import ru.otus.hw18.dto.BookDto;
import ru.otus.hw18.dto.GenreDto;
import ru.otus.hw18.exceptions.EntityNotFoundException;
import ru.otus.hw18.models.Author;
import ru.otus.hw18.models.Book;
import ru.otus.hw18.models.Genre;
import ru.otus.hw18.repositories.AuthorRepository;
import ru.otus.hw18.repositories.BookRepository;
import ru.otus.hw18.repositories.GenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    private final RateLimiter rateLimiter;

    private final CircuitBreaker circuitBreaker;

    private final CheckedSupplier<List<Book>> findBooksSupplier;

    public BookServiceImpl(AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           BookRepository bookRepository,
                           BookConverter bookConverter,
                           RateLimiter rateLimiter,
                           CircuitBreaker circuitBreaker) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.rateLimiter = rateLimiter;
        this.circuitBreaker = circuitBreaker;

        findBooksSupplier = RateLimiter.decorateCheckedSupplier(
                rateLimiter,
                () -> circuitBreaker.run(bookRepository::findAll, t -> List.of())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToBookDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        try {
            return findBooksSupplier.get()
                    .stream()
                    .map(bookConverter::bookToBookDto)
                    .toList();
        } catch (Throwable e) {
            log.error("Ошибка получения списка авторов: {}", e.getMessage());
            return List.of();
        }
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
