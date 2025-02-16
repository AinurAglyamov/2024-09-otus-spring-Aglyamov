package ru.otus.hw13.services;

import ru.otus.hw13.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
