package ru.otus.hw.school.services.homeworkstatus;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.TeacherRepository;

import java.util.Set;

@Service
@Slf4j
public class SentStatusService extends HomeworkStatusService {

    public SentStatusService(HomeworkRepository homeworkRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        super(
                Set.of(HomeworkStatus.NEW, HomeworkStatus.REWORK),
                HomeworkStatus.SENT,
                homeworkRepository,
                teacherRepository,
                modelMapper
        );
    }

    @Override
    protected boolean needsSetTeacher() {
        return false;
    }
}
