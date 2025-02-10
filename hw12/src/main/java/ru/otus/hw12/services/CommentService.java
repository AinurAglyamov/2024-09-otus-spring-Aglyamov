package ru.otus.hw12.services;

import ru.otus.hw12.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CommentDto> findById(long id);

    List<CommentDto> findAllByBookId(long bookId);

    CommentDto insert(String text, long bookId);

    CommentDto update(long id, String text);

    void deleteById(long id);
}
