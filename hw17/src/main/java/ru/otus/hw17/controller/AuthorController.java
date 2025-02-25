package ru.otus.hw17.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw17.dto.AuthorDto;
import ru.otus.hw17.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }
}
