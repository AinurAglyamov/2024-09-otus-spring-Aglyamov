package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.GroupDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис курсов
 */
public interface CourseService {

    /**
     * Получить список курсов
     * @param pageable параметры постраничной выборки
     * @return список курсов
     */
    Page<CourseDto> getAll(Pageable pageable);

    List<GroupDto> getGroupsByCourseId(UUID courseId);

    /**
     * Получить курс по идентификатору
     * @param id курс
     * @return курс
     */
    CourseDto getById(UUID id);

    /**
     * Создание курса
     * @param groupDto курс
     * @return созданный курс
     */
    CourseDto create(CourseDto groupDto);

    /**
     * Обновление курса
     * @param groupDto курс
     * @return обновленный курс
     */
    CourseDto update(CourseDto groupDto);

    /**
     * Удаление курса по id
     * @param uuid идентификатор курса
     */
    void deleteById(UUID uuid);

}
