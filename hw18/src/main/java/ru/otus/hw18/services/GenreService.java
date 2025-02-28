package ru.otus.hw18.services;

import ru.otus.hw18.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
