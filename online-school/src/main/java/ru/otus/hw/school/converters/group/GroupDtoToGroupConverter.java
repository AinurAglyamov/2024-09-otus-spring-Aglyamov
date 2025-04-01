package ru.otus.hw.school.converters.group;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.models.Group;

public class GroupDtoToGroupConverter implements Converter<GroupDto, Group> {

    @Override
    public Group convert(MappingContext<GroupDto, Group> mappingContext) {
        GroupDto group = mappingContext.getSource();

        return new Group()
                .setGroupName(group.getGroupName())
                .setStartDate(group.getStartDate())
                .setEndDate(group.getEndDate());
    }
}
