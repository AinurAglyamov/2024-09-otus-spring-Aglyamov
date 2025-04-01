package ru.otus.hw.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.school.models.Teacher;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий преподавателей
 */
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    List<Teacher> findAllByIdIn(List<UUID> ids);
}
