package ru.otus.hw.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.school.models.Course;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий курсов
 */
public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findAllByIdIn(List<UUID> ids);
}
