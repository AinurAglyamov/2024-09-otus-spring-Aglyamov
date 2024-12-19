package ru.otus.hw08.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw08.converters.AuthorConverter;
import ru.otus.hw08.dto.AuthorDto;
import ru.otus.hw08.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorConverter::authorToAuthorDto)
                .toList();
    }
}
