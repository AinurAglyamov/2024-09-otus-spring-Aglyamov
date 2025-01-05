package ru.otus.hw10.services;

import ru.otus.hw10.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    BookDto save(BookDto bookDto);

    void deleteById(long id);
}