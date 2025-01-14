package ru.otus.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.converters.GenreConverter;
import ru.otus.hw11.dto.GenreDto;
import ru.otus.hw11.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    private final GenreConverter genreConverter;

    @GetMapping("/api/genre")
    public Flux<GenreDto> getGenres() {
        return genreRepository.findAll()
                .map(genreConverter::genreToGenreDto);
    }
}
