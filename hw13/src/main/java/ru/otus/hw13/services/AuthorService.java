package ru.otus.hw13.services;

import ru.otus.hw13.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
