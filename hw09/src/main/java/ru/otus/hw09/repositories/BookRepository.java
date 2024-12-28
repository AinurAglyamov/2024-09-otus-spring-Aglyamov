package ru.otus.hw09.repositories;

import ru.otus.hw09.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph("book-entity-graph")
    List<Book> findAll();
}
