package ru.otus.hw13.services;

import ru.otus.hw13.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    BookDto update(BookDto bookDto);

    BookDto create(BookDto bookDto);

    void deleteById(long id);
}
