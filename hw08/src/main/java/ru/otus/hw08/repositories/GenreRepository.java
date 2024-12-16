package ru.otus.hw08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.models.Genre;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, BigInteger> {
    Optional<Genre> findById(BigInteger genreId);

    List<Genre> findAll();
}
