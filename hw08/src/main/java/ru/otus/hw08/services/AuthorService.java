package ru.otus.hw08.services;

import ru.otus.hw08.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
