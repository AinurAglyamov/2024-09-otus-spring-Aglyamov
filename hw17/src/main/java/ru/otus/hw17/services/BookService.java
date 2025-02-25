package ru.otus.hw17.services;

import ru.otus.hw17.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    BookDto save(BookDto bookDto);

    void deleteById(long id);
}
