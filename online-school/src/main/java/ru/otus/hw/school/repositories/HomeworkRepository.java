package ru.otus.hw.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.school.models.Homework;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий домашних работ
 */
public interface HomeworkRepository extends JpaRepository<Homework, UUID> {

    List<Homework> findAllByTeacherId(UUID teacherId);

    List<Homework> findAllByStudentId(UUID studentId);
}
