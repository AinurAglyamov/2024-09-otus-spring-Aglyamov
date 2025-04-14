package ru.otus.hw.school.converters.homework;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.models.Homework;

public class HomeworkDtoToHomeworkConverter implements Converter<HomeworkDto, Homework> {

    @Override
    public Homework convert(MappingContext<HomeworkDto, Homework> context) {
        HomeworkDto homeworkDto = context.getSource();

        return new Homework();
    }
}
