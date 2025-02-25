package ru.otus.hw17.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw17.converters.AuthorConverter;
import ru.otus.hw17.dto.AuthorDto;
import ru.otus.hw17.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorConverter::authorToAuthorDto)
                .toList();
    }
}
