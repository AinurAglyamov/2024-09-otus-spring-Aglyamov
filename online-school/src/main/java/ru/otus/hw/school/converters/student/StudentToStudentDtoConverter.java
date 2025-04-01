package ru.otus.hw.school.converters.student;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.models.Student;

public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(MappingContext<Student, StudentDto> mappingContext) {
        Student student = mappingContext.getSource();

        return new StudentDto()
                .setId(student.getId())
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setCountry(student.getCountry())
                .setEmail(student.getEmail())
                .setPhoneNumber(student.getPhoneNumber());
    }
}
