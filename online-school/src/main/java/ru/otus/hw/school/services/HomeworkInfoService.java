package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.HomeworkInfoDto;

import java.util.UUID;

/**
 * Сервис для работы с домашними работами
 */
public interface HomeworkInfoService {

    /**
     * Получить список домашних работ
     * @param pageable параметры постраничной выборки
     * @return список домашних работ
     */
    Page<HomeworkInfoDto> getAll(Pageable pageable);

    /**
     * Получить домашнюю работу по идентификатору
     * @param id идентификатор группы обучения
     * @return группа обучения
     */
    HomeworkInfoDto getById(UUID id);

    /**
     * Создание сущности домашней работы
     * @param homeworkInfoDto домашняя работа
     * @return созданная домашняя работа
     */
    HomeworkInfoDto create(HomeworkInfoDto homeworkInfoDto);

    /**
     * Обновление домашней работы
     * @param homeworkInfoDto домашняя работа
     * @return обновленная домашняя работа
     */
    HomeworkInfoDto update(HomeworkInfoDto homeworkInfoDto);

    /**
     * Удаление домашняя работа по id
     * @param uuid идентификатор домашней работы
     */
    void deleteById(UUID uuid);

}
