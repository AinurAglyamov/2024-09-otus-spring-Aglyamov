package ru.otus.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.converters.AuthorConverter;
import ru.otus.hw11.dto.AuthorDto;
import ru.otus.hw11.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    @GetMapping("/api/author")
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(authorConverter::authorToAuthorDto);
    }
}
