package ru.otus.hw.school.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.StudentDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис студентов
 */
public interface StudentService {

    /**
     * Получить список студентов
     * @param pageable параметры постраничной выборки
     * @return список студентов
     */
    Page<StudentDto> getAll(Pageable pageable);

    /**
     * Получить список групп по идентификатору студента
     * @param studentId идентификатор студента
     * @return список групп
     */
    List<GroupDto> getGroupsByStudentId(UUID studentId);

    /**
     * Получить список домашних работ по идентификатору студента
     * @param studentId идентификатор студента
     * @return список домашних работа
     */
    List<HomeworkDto> getHomeworksByStudentId(UUID studentId);

    /**
     * Получить студента по идентификатору
     * @param id идентификатор студента
     * @return студент
     */
    StudentDto getById(UUID id);

    /**
     * Создать студента
     * @param student студент
     * @return созданный студент
     */
    StudentDto create(StudentDto student);

    /**
     * Создать студента
     * @param student студент
     * @return созданный студент
     */
    StudentDto update(StudentDto student);

    /**
     * Удалить студента по идентификатору
     * @param id идентификатор студента
     */
    void deleteById(UUID id);

}
