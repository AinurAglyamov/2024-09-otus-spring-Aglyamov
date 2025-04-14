package ru.otus.hw.school.converters.homeworkhistory;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.otus.hw.school.dto.HomeworkHistoryDto;
import ru.otus.hw.school.models.HomeworkHistory;

public class HomeworkHistoryToHomeworkHistoryDtoConverter implements Converter<HomeworkHistory, HomeworkHistoryDto> {

    @Override
    public HomeworkHistoryDto convert(MappingContext<HomeworkHistory, HomeworkHistoryDto> context) {
        HomeworkHistory historyItem = context.getSource();

        return new HomeworkHistoryDto()
                .setId(historyItem.getId())
                .setStatus(historyItem.getStatus())
                .setMessage(historyItem.getMessage())
                .setCreatedAt(historyItem.getCreatedAt());
    }
}
