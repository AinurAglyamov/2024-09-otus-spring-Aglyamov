package ru.otus.hw12.services;

import ru.otus.hw12.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
