package ru.otus.hw08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.models.Author;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, BigInteger> {

    @Override
    List<Author> findAll();

}
