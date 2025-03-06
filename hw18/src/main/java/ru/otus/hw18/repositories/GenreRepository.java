package ru.otus.hw18.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw18.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
