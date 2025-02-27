package ru.otus.hw17.services;

import ru.otus.hw17.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
