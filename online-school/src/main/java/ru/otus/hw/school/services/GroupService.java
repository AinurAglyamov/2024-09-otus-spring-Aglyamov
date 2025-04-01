package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.StudentDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис групп обучения
 */
public interface GroupService {

    /**
     * Получить список групп
     * @param pageable параметры постраничной выборки
     * @return список групп
     */
    Page<GroupDto> getAll(Pageable pageable);

    /**
     * Получение студентов по идентификатору группы
     * @param groupId идентификатор группы
     * @return список студентов
     */
    List<StudentDto> getStudentsByGroupId(UUID groupId);

    /**
     * Получить группу обучения по идентификатору
     * @param id идентификатор группы обучения
     * @return группа обучения
     */
    GroupDto getById(UUID id);

    /**
     * Создание группы обучения
     * @param groupDto группа обучения
     * @return созданная группа обучения
     */
    GroupDto create(GroupDto groupDto);

    /**
     * Обновление группы обучения
     * @param groupDto группа обучения
     * @return обновленная группа обучения
     */
    GroupDto update(GroupDto groupDto);

    /**
     * Удаление группы по id
     * @param uuid идентификатор группы
     */
    void deleteById(UUID uuid);

}
