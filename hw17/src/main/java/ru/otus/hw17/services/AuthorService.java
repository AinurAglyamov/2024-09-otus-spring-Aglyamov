package ru.otus.hw17.services;

import ru.otus.hw17.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
