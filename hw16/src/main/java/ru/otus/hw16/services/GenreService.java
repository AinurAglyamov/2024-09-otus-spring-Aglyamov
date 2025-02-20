package ru.otus.hw16.services;

import ru.otus.hw16.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
