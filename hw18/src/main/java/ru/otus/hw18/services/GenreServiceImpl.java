package ru.otus.hw18.services;

import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw18.converters.GenreConverter;
import ru.otus.hw18.dto.GenreDto;
import ru.otus.hw18.models.Genre;
import ru.otus.hw18.repositories.GenreRepository;

import java.util.List;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreConverter genreConverter;

    private final RateLimiter rateLimiter;

    private final CircuitBreaker circuitBreaker;

    private final CheckedSupplier<List<Genre>> findGenresSupplier;

    public GenreServiceImpl(GenreRepository genreRepository,
                            GenreConverter genreConverter,
                            RateLimiter rateLimiter,
                            CircuitBreaker circuitBreaker) {
        this.genreRepository = genreRepository;
        this.genreConverter = genreConverter;
        this.rateLimiter = rateLimiter;
        this.circuitBreaker = circuitBreaker;

        findGenresSupplier = RateLimiter.decorateCheckedSupplier(
                rateLimiter,
                () -> circuitBreaker.run(genreRepository::findAll, t -> List.of())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        try {
            return findGenresSupplier.get()
                    .stream()
                    .map(genreConverter::genreToGenreDto)
                    .toList();
        } catch (Throwable e) {
            log.error("Ошибка получения списка авторов: {}", e.getMessage());
            return List.of();
        }
    }
}
