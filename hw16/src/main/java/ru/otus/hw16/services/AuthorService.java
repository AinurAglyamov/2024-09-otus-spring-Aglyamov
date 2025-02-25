package ru.otus.hw16.services;

import ru.otus.hw16.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
