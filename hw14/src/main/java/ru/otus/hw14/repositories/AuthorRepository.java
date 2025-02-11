package ru.otus.hw14.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw14.models.relational.Author;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Override
    @EntityGraph(attributePaths = "books")
    List<Author> findAll();

    @Query("select a from Author a where a.mongoId in :mongoIds")
    List<Author> findAllByMongoId(Set<BigInteger> mongoIds);
}
