package ru.otus.hw.school.converters.group;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.models.Group;

import java.util.Optional;

public class GroupToGroupDtoConverter implements Converter<Group, GroupDto> {

    @Override
    public GroupDto convert(MappingContext<Group, GroupDto> mappingContext) {
        Group group = mappingContext.getSource();

        CourseDto course = Optional.ofNullable(group.getCourse())
                .map(c -> new CourseDto()
                        .setId(c.getId())
                        .setCourseName(c.getCourseName())
                )
                .orElse(null);

        return new GroupDto()
                .setId(group.getId())
                .setGroupName(group.getGroupName())
                .setStatus(group.getStatus())
                .setStartDate(group.getStartDate())
                .setEndDate(group.getEndDate())
                .setCreatedAt(group.getCreatedAt())
                .setUpdatedAt(group.getUpdatedAt())
                .setCourse(course);
    }
}
