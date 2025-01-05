package ru.otus.hw10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw10.dto.AuthorDto;
import ru.otus.hw10.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }
}
