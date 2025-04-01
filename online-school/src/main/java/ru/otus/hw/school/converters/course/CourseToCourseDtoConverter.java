package ru.otus.hw.school.converters.course;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Course;

import java.util.List;
import java.util.Optional;

public class CourseToCourseDtoConverter implements Converter<Course, CourseDto> {

    @Override
    public CourseDto convert(MappingContext<Course, CourseDto> mappingContext) {
        Course course = mappingContext.getSource();

        var teachers = Optional.ofNullable(course.getTeachers()).orElse(List.of()).stream()
                .map(teacher -> new TeacherDto()
                        .setId(teacher.getId())
                        .setFirstName(teacher.getFirstName())
                        .setLastName(teacher.getLastName())
                ).toList();

        return new CourseDto()
                .setId(course.getId())
                .setCourseName(course.getCourseName())
                .setCost(course.getCost())
                .setDescription(course.getDescription())
                .setTeachers(teachers)
                .setCreatedAt(course.getCreatedAt())
                .setUpdatedAt(course.getUpdatedAt());
    }
}
