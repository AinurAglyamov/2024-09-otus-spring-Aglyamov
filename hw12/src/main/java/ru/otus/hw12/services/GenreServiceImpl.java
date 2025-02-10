package ru.otus.hw12.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw12.converters.GenreConverter;
import ru.otus.hw12.dto.GenreDto;
import ru.otus.hw12.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreConverter genreConverter;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genreConverter::genreToGenreDto)
                .toList();
    }
}
