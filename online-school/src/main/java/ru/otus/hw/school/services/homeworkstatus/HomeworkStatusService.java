package ru.otus.hw.school.services.homeworkstatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.homeworkstatus.ChangeHomeworkStatusRequest;
import ru.otus.hw.school.exception.BusinessLogicException;
import ru.otus.hw.school.models.Homework;
import ru.otus.hw.school.models.HomeworkHistory;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.TeacherRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public abstract class HomeworkStatusService {

    protected final Set<HomeworkStatus> allowedStatuses;
    protected final HomeworkStatus homeworkStatus;
    protected final HomeworkRepository homeworkRepository;
    protected final TeacherRepository teacherRepository;
    protected final ModelMapper modelMapper;

    @Transactional
    public HomeworkDto change(ChangeHomeworkStatusRequest request) {
        log.info("Смена статуса на {}", homeworkStatus);

        UUID homeworkId = request.getHomeworkId();
        var homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new EntityNotFoundException("ДЗ с id %s не найдено".formatted(homeworkId)));

        HomeworkStatus currentStatus = homework.getStatus();

        if (currentStatus == homeworkStatus) {
            return modelMapper.map(homework, HomeworkDto.class);
        }

        if (!allowedStatuses.contains(currentStatus)) {
            throw new BusinessLogicException(
                    "Невозможно перевести ДЗ из статуса %s в статус %s".formatted(currentStatus.name(), homeworkStatus.name())
            );
        }

        if (needsSetTeacher()) {
            UUID teacherId = request.getTeacherId();
            var teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Преподаватель с id %s не найден".formatted(teacherId)));

            homework.setTeacher(teacher);
        }

        homework.setStatus(homeworkStatus);
        homework.setUpdatedAt(Instant.now());
        setMark(homework, request.getMark());
        addToHistory(homework, request);

        homeworkRepository.save(homework);

        return modelMapper.map(homework, HomeworkDto.class);
    }

    protected boolean needsSetTeacher() {
        return true;
    }

    protected void setMark(Homework homework, Integer mark) {
        if (mark != null) {
            throw new BusinessLogicException("Невозможно ставить оценку в статусе %s".formatted(homeworkStatus));
        }
    }

    private void addToHistory(Homework homework, ChangeHomeworkStatusRequest request) {
        var historyItem = new HomeworkHistory();
        historyItem.setMessage(request.getMessage());
        historyItem.setStatus(homeworkStatus);
        historyItem.setCreatedAt(Instant.now());
        historyItem.setHomework(homework);

        var historyList = Optional.ofNullable(homework.getHistoryItems()).orElse(new ArrayList<>());
        historyList.add(historyItem);
    }

    public HomeworkStatus getProcessingStatus() {
        return homeworkStatus;
    }
}
