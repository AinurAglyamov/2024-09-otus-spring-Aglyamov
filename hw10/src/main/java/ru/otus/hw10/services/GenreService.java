package ru.otus.hw10.services;

import ru.otus.hw10.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
