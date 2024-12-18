package ru.otus.hw08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.models.Book;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, BigInteger> {

    List<Book> findAll();

    Optional<Book> findById(BigInteger id);

    Book save(Book book);

    void deleteById(BigInteger id);
}
