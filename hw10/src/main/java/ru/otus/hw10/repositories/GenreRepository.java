package ru.otus.hw10.repositories;

import ru.otus.hw10.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
