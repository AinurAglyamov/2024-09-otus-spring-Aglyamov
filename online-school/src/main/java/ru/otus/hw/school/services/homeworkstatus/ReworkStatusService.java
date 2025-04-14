package ru.otus.hw.school.services.homeworkstatus;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.TeacherRepository;

import java.util.Set;

@Service
public class ReworkStatusService extends HomeworkStatusService{

    public ReworkStatusService(HomeworkRepository homeworkRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        super(
                Set.of(HomeworkStatus.CHECKING),
                HomeworkStatus.REWORK,
                homeworkRepository,
                teacherRepository,
                modelMapper
        );
    }
}
