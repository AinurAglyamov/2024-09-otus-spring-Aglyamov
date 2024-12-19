package ru.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.models.Author;

import java.math.BigInteger;

public interface AuthorRepository extends MongoRepository<Author, BigInteger> {
}
