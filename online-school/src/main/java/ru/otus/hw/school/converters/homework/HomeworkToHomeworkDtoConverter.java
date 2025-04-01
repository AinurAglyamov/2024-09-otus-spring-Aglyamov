package ru.otus.hw.school.converters.homework;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Homework;

import java.util.Optional;

public class HomeworkToHomeworkDtoConverter implements Converter<Homework, HomeworkDto> {

    @Override
    public HomeworkDto convert(MappingContext<Homework, HomeworkDto> context) {
        Homework homework = context.getSource();

        HomeworkInfoDto homeworkInfoDto = Optional.ofNullable(homework.getHomeworkInfo())
                .map(homeworkInfo -> new HomeworkInfoDto()
                        .setId(homeworkInfo.getId())
                        .setTopic(homeworkInfo.getTopic())
                ).orElse(null);

        StudentDto studentDto = Optional.of(homework.getStudent())
                .map(student -> new StudentDto()
                        .setId(student.getId())
                        .setFirstName(student.getFirstName())
                        .setLastName(student.getLastName())
                ).orElseThrow(() -> new IllegalStateException("ДЗ с id %s не имеет студента".formatted(homework.getId())));

        TeacherDto teacherDto = Optional.ofNullable(homework.getTeacher())
                .map(teacher -> new TeacherDto()
                        .setId(teacher.getId())
                        .setFirstName(teacher.getFirstName())
                        .setLastName(teacher.getLastName())
                ).orElse(null);

        return new HomeworkDto()
                .setId(homework.getId())
                .setHomeworkInfo(homeworkInfoDto)
                .setStudent(studentDto)
                .setTeacher(teacherDto)
                .setStatus(homework.getStatus())
                .setMark(homework.getMark());
    }

}
