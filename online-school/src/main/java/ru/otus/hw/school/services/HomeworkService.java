package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.HomeworkHistoryDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с домашними работами
 */
public interface HomeworkService {

    Page<HomeworkDto> getAll(Pageable pageable);

    List<HomeworkDto> getAllByStudentId(UUID studentId);

    List<HomeworkDto> getAllByTeacherId(UUID teacherId);

    List<HomeworkHistoryDto> getHistory(UUID homeworkId);

    /**
     * Получить домашнюю работу по идентификатору
     * @param id идентификатор группы обучения
     * @return группа обучения
     */
    HomeworkDto getById(UUID id);

    /**
     * Создание (взятие в работу) добамшней работы
     * @param homeworkDto домашняя работа
     * @return домашняя работа
     */
    HomeworkDto create(HomeworkDto homeworkDto);

    /**
     * Удаление домашняя работа по id
     * @param uuid идентификатор домашней работы
     */
    void deleteById(UUID uuid);

}
