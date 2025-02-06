package ru.otus.hw13.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw13.converters.BookConverter;
import ru.otus.hw13.dto.AuthorDto;
import ru.otus.hw13.dto.BookDto;
import ru.otus.hw13.dto.GenreDto;
import ru.otus.hw13.exceptions.EntityNotFoundException;
import ru.otus.hw13.models.Author;
import ru.otus.hw13.models.Book;
import ru.otus.hw13.models.Genre;
import ru.otus.hw13.repositories.AuthorRepository;
import ru.otus.hw13.repositories.BookRepository;
import ru.otus.hw13.repositories.GenreRepository;
import ru.otus.hw13.security.service.AclServiceWrapperService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.acls.domain.BasePermission.DELETE;
import static org.springframework.security.acls.domain.BasePermission.READ;
import static org.springframework.security.acls.domain.BasePermission.WRITE;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    private final AclServiceWrapperService aclServiceWrapperService;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("canRead(#id, T(ru.otus.hw13.dto.BookDto))")
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToBookDto);
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::bookToBookDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        BookDto savedBook = save(bookDto, new Book());
        aclServiceWrapperService.createPermissions(savedBook, READ, WRITE, DELETE);

        return savedBook;
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#bookDto, 'WRITE')")
    public BookDto update(BookDto bookDto) {
        long id = bookDto.getId();
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));;

        return save(bookDto, book);
    }

    private BookDto save(BookDto bookDto, Book book) {
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
    @PreAuthorize("canDelete(#id, T(ru.otus.hw13.dto.BookDto))")
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

}
