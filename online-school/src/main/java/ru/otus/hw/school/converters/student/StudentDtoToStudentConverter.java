package ru.otus.hw.school.converters.student;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.models.Student;

public class StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

    @Override
    public Student convert(MappingContext<StudentDto, Student> mappingContext) {
        StudentDto student = mappingContext.getSource();

        return new Student()
                .setFirstName(student.getFirstName())
                .setLastName(student.getFirstName())
                .setCountry(student.getCountry())
                .setEmail(student.getEmail())
                .setPhoneNumber(student.getPhoneNumber());
    }
}
