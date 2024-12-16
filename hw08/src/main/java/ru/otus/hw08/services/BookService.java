package ru.otus.hw08.services;

import ru.otus.hw08.dto.BookDto;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(BigInteger id);

    List<BookDto> findAll();

    BookDto insert(String title, BigInteger authorId, BigInteger genreId);

    BookDto update(BigInteger id, String title, BigInteger authorId, BigInteger genreId);

    void deleteById(BigInteger id);
}
