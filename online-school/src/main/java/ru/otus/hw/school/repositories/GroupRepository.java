package ru.otus.hw.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.school.models.Group;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий преподавателей
 */
public interface GroupRepository extends JpaRepository<Group, UUID> {

    List<Group> findAllByIdIn(List<UUID> ids);
}
