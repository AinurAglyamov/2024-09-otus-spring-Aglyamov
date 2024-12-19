package ru.otus.hw08.services;

import ru.otus.hw08.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
