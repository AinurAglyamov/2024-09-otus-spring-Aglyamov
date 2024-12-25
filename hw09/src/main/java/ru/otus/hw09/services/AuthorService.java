package ru.otus.hw09.services;

import ru.otus.hw09.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
