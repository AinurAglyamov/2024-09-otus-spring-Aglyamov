package ru.otus.hw09.services;

import ru.otus.hw09.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
