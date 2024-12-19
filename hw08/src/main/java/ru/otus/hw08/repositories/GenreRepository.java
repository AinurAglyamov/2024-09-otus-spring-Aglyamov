package ru.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.models.Genre;

import java.math.BigInteger;

public interface GenreRepository extends MongoRepository<Genre, BigInteger> {
}
