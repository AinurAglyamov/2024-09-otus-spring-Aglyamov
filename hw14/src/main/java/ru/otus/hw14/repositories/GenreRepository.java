package ru.otus.hw14.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw14.models.relational.Genre;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.mongoId in :mongoIds")
    List<Genre> findAllByMongoIds(Set<BigInteger> mongoIds);
}
