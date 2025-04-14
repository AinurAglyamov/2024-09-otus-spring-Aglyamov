package ru.otus.hw.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.school.models.HomeworkInfo;

import java.util.UUID;

/**
 * Репозиторий домашних работ
 */
public interface HomeworkInfoRepository extends JpaRepository<HomeworkInfo, UUID> {
}
