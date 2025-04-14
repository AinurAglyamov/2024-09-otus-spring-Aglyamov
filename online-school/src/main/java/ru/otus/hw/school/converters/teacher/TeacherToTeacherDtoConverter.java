package ru.otus.hw.school.converters.teacher;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Teacher;

public class TeacherToTeacherDtoConverter implements Converter<Teacher, TeacherDto> {

    @Override
    public TeacherDto convert(MappingContext<Teacher, TeacherDto> mappingContext) {
        Teacher teacher = mappingContext.getSource();

        return new TeacherDto()
                .setId(teacher.getId())
                .setFirstName(teacher.getFirstName())
                .setLastName(teacher.getLastName())
                .setCountry(teacher.getCountry())
                .setEmail(teacher.getEmail())
                .setPhoneNumber(teacher.getPhoneNumber());
    }
}
