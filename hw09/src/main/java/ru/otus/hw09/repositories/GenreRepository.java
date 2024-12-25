package ru.otus.hw09.repositories;

import ru.otus.hw09.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
