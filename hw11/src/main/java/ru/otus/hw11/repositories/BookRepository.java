package ru.otus.hw11.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.hw11.models.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}