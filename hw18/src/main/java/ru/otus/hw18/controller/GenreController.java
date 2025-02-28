package ru.otus.hw18.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw18.dto.GenreDto;
import ru.otus.hw18.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/api/genres")
    public ResponseEntity<List<GenreDto>> getGenres() {
        List<GenreDto> all = genreService.findAll();
        return ResponseEntity.ok(all);
    }
}
