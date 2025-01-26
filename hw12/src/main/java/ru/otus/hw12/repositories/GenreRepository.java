package ru.otus.hw12.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw12.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
