package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.TeacherDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис преподавателей
 */
public interface TeacherService {

    /**
     * Получение списка преподавателей
     * @param pageable параметры постраничной выборки
     * @return список преподавателей
     */
    Page<TeacherDto> getAll(Pageable pageable);

    /**
     * Получить преподавателя по идентификатору
     * @param id идентификатор преподавателя
     * @return преподаватель
     */
    TeacherDto getById(UUID id);

    /**
     * Создать преподавателя
     * @param student преподаватель
     * @return созданный преподаватель
     */
    TeacherDto create(TeacherDto student);

    /**
     * Создать преподавателя
     * @param student преподаватель
     * @return созданный преподаватель
     */
    TeacherDto update(TeacherDto student);

    /**
     * Удалить преподавателя по идентификатору
     * @param id идентификатор преподавателя
     */
    void deleteById(UUID id);

    /**
     * Получение домашних работ по идентификатору преподавателя (где он/она является проверяющим)
     * @param teacherId идентификатор преподавателя
     * @return список домашних работ преподавателя
     */
    List<HomeworkDto> getHomeworksByTeacherId(UUID teacherId);
}
