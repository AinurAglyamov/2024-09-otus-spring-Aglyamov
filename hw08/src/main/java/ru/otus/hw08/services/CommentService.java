package ru.otus.hw08.services;

import ru.otus.hw08.dto.CommentDto;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CommentDto> findById(BigInteger id);

    List<CommentDto> findAllByBookId(BigInteger bookId);

    CommentDto insert(String text, BigInteger bookId);

    CommentDto update(BigInteger id, String text);

    void deleteById(BigInteger id);
}
