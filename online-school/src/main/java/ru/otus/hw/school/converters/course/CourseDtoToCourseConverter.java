package ru.otus.hw.school.converters.course;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.models.Course;

public class CourseDtoToCourseConverter implements Converter<CourseDto, Course> {

    @Override
    public Course convert(MappingContext<CourseDto, Course> mappingContext) {
        CourseDto course = mappingContext.getSource();

        return new Course()
                .setCourseName(course.getCourseName())
                .setCost(course.getCost())
                .setDescription(course.getDescription());
    }
}
