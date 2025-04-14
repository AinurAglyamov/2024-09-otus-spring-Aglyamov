package ru.otus.hw.school.converters.homeworkinfo;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.models.HomeworkInfo;

public class HomeworkInfoDtoToHomeworkConverter implements Converter<HomeworkInfoDto, HomeworkInfo> {

    @Override
    public HomeworkInfo convert(MappingContext<HomeworkInfoDto, HomeworkInfo> mappingContext) {
        HomeworkInfoDto homework = mappingContext.getSource();

        return new HomeworkInfo()
                .setTopic(homework.getTopic());
    }
}
