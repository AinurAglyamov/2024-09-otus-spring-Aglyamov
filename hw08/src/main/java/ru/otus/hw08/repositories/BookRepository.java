package ru.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.models.Book;

import java.math.BigInteger;

public interface BookRepository extends MongoRepository<Book, BigInteger> {
}
