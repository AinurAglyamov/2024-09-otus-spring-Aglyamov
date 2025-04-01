package ru.otus.hw.school.converters.teacher;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Teacher;

public class TeacherDtoToTeacherConverter implements Converter<TeacherDto, Teacher> {

    @Override
    public Teacher convert(MappingContext<TeacherDto, Teacher> mappingContext) {
        TeacherDto teacher = mappingContext.getSource();

        return new Teacher()
                .setId(teacher.getId())
                .setFirstName(teacher.getFirstName())
                .setLastName(teacher.getLastName())
                .setCountry(teacher.getCountry())
                .setEmail(teacher.getEmail())
                .setPhoneNumber(teacher.getPhoneNumber());
    }
}
