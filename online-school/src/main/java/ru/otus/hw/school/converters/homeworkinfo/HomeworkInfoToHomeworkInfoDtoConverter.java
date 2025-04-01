package ru.otus.hw.school.converters.homeworkinfo;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.models.HomeworkInfo;

import java.util.Optional;

public class HomeworkInfoToHomeworkInfoDtoConverter implements Converter<HomeworkInfo, HomeworkInfoDto> {

    @Override
    public HomeworkInfoDto convert(MappingContext<HomeworkInfo, HomeworkInfoDto> mappingContext) {
        HomeworkInfo homeworkInfo = mappingContext.getSource();

        CourseDto course = Optional.ofNullable(homeworkInfo.getCourse())
                .map(c -> new CourseDto()
                        .setCourseName(c.getCourseName())
                )
                .orElse(null);

        return new HomeworkInfoDto()
                .setId(homeworkInfo.getId())
                .setTopic(homeworkInfo.getTopic())
                .setCourse(course);
    }
}
