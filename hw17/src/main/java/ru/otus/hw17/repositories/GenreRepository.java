package ru.otus.hw17.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw17.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
