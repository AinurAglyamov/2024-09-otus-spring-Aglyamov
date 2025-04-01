package ru.otus.hw.school.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.HomeworkHistoryDto;
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.exception.BusinessLogicException;
import ru.otus.hw.school.models.Homework;
import ru.otus.hw.school.models.HomeworkHistory;
import ru.otus.hw.school.models.HomeworkInfo;
import ru.otus.hw.school.models.Student;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.repositories.HomeworkInfoRepository;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.StudentRepository;
import ru.otus.hw.school.repositories.TeacherRepository;
import ru.otus.hw.school.services.HomeworkService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final HomeworkInfoRepository homeworkInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<HomeworkDto> getAll(Pageable pageable) {
        Page<Homework> homeworkPage = homeworkRepository.findAll(pageable);
        List<HomeworkDto> homeworksDtos = homeworkPage.stream()
                .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                .toList();

        return new PageImpl<>(homeworksDtos, homeworkPage.getPageable(), homeworkPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getAllByStudentId(UUID studentId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getAllByTeacherId(UUID teacherId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkHistoryDto> getHistory(UUID homeworkId) {
        var homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ с id %s не найдено".formatted(homeworkId))
                );

        List<HomeworkHistory> historyItems = homework.getHistoryItems();

        return historyItems.stream()
                .map(historyItem -> modelMapper.map(historyItem, HomeworkHistoryDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkDto getById(UUID id) {
        var homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ с id %s не найдено".formatted(id))
        );

        return modelMapper.map(homework, HomeworkDto.class);
    }

    @Override
    @Transactional
    public HomeworkDto create(HomeworkDto homeworkDto) {
        var homework = new Homework();

        UUID homeworkInfoId = Optional.ofNullable(homeworkDto.getHomeworkInfo())
                .map(HomeworkInfoDto::getId)
                .orElseThrow(() -> new BusinessLogicException("Не передан id ДЗ Инфо"));

        HomeworkInfo homeworkInfo = homeworkInfoRepository.findById(homeworkInfoId)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ Инфо с id %s не найден".formatted(homeworkInfoId)));

        UUID studentId = Optional.ofNullable(homeworkDto.getStudent())
                .map(StudentDto::getId)
                .orElseThrow(() -> new BusinessLogicException("Не передан id студента"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Студент с id %s не найден".formatted(studentId)));

        homework
                .setHomeworkInfo(homeworkInfo)
                .setStudent(student)
                .setStatus(HomeworkStatus.NEW)
                .setStartDate(LocalDate.now())
                .setCreatedAt(Instant.now());

        var historyItem = new HomeworkHistory();
        historyItem.setStatus(HomeworkStatus.NEW);
        historyItem.setCreatedAt(Instant.now());
        historyItem.setHomework(homework);

        var historyList = new ArrayList<HomeworkHistory>();
        historyList.add(historyItem);

        homeworkRepository.save(homework);
        homework.setHistoryItems(historyList);

        return modelMapper.map(homework, HomeworkDto.class);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        homeworkRepository.deleteById(id);
    }

}
