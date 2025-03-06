package ru.otus.hw18.services;

import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw18.converters.AuthorConverter;
import ru.otus.hw18.dto.AuthorDto;
import ru.otus.hw18.models.Author;
import ru.otus.hw18.repositories.AuthorRepository;

import java.util.List;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    private final RateLimiter rateLimiter;

    private final CircuitBreaker circuitBreaker;

    private final CheckedSupplier<List<Author>> findAuthorsSupplier;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             AuthorConverter authorConverter,
                             RateLimiter rateLimiter,
                             CircuitBreaker circuitBreaker) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
        this.rateLimiter = rateLimiter;
        this.circuitBreaker = circuitBreaker;

        findAuthorsSupplier = RateLimiter.decorateCheckedSupplier(
                rateLimiter,
                () -> circuitBreaker.run(authorRepository::findAll, t -> List.of())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        try {
            return findAuthorsSupplier.get()
                    .stream()
                    .map(authorConverter::authorToAuthorDto)
                    .toList();
        } catch (Throwable e) {
            log.error("Ошибка получения списка авторов: {}", e.getMessage());
            return List.of();
        }
    }
}
