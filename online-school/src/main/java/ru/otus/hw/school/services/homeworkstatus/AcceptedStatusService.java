package ru.otus.hw.school.services.homeworkstatus;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.hw.school.exception.BusinessLogicException;
import ru.otus.hw.school.models.Homework;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.TeacherRepository;

import java.time.LocalDate;
import java.util.Set;

@Service
public class AcceptedStatusService extends HomeworkStatusService{

    public AcceptedStatusService(HomeworkRepository homeworkRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        super(
                Set.of(HomeworkStatus.CHECKING),
                HomeworkStatus.ACCEPTED,
                homeworkRepository,
                teacherRepository,
                modelMapper
        );
    }

    @Override
    protected void setMark(Homework homework, Integer mark) {
        if (mark == null) {
            throw new BusinessLogicException("Необходимо указать оценку за домашнюю работу");
        }
        homework.setMark(mark);
        homework.setEndDate(LocalDate.now());
    }
}
