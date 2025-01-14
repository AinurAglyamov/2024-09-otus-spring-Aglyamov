package ru.otus.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.converters.BookConverter;
import ru.otus.hw11.dto.AuthorDto;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.dto.GenreDto;
import ru.otus.hw11.exceptions.EntityNotFoundException;
import ru.otus.hw11.models.Book;
import ru.otus.hw11.repositories.BookRepositoryCustom;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepositoryCustom bookRepository;

    private final BookConverter bookConverter;

    @GetMapping("/api/book")
    public Flux<BookDto> getBooks() {
        return bookRepository.findAll()
                .map(bookConverter::bookToBookDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable long id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToBookDto)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(id)))
                )
                .map(ResponseEntity::ok);
    }

    @PostMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> createBook(@RequestBody BookDto bookDto) {
        return Mono.just(new Book())
                .map(book -> setFields(bookDto, book))
                .flatMap(bookRepository::save)
                .map(bookConverter::bookToBookDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> editBook(@RequestBody BookDto bookDto) {
        return Mono.just(bookDto.getId())
                .flatMap(id -> bookRepository.findById(id).switchIfEmpty(
                        Mono.error(new EntityNotFoundException("Book with id %d not found".formatted(id)))
                ))
                .map(book -> setFields(bookDto, book))
                .flatMap(bookRepository::save)
                .map(bookConverter::bookToBookDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") long id) {
        return bookRepository.deleteById(id)
                .map(ResponseEntity::ok);
    }

    private Book setFields(BookDto bookDto, Book book) {
        return book.setTitle(bookDto.getTitle())
                .setAuthorId(Optional.ofNullable(bookDto.getAuthor())
                        .map(AuthorDto::getId)
                        .orElse(null))
                .setGenreId(Optional.ofNullable(bookDto.getGenre())
                        .map(GenreDto::getId)
                        .orElse(null));
    }
}
