package ru.otus.hw18.services;

import ru.otus.hw18.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
