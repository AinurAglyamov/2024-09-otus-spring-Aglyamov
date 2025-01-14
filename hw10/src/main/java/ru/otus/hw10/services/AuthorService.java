package ru.otus.hw10.services;

import ru.otus.hw10.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
