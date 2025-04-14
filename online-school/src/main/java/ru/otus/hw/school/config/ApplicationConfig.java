package ru.otus.hw.school.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.school.converters.course.CourseDtoToCourseConverter;
import ru.otus.hw.school.converters.course.CourseToCourseDtoConverter;
import ru.otus.hw.school.converters.group.GroupDtoToGroupConverter;
import ru.otus.hw.school.converters.group.GroupToGroupDtoConverter;
import ru.otus.hw.school.converters.homework.HomeworkDtoToHomeworkConverter;
import ru.otus.hw.school.converters.homework.HomeworkToHomeworkDtoConverter;
import ru.otus.hw.school.converters.homeworkhistory.HomeworkHistoryToHomeworkHistoryDtoConverter;
import ru.otus.hw.school.converters.homeworkinfo.HomeworkInfoDtoToHomeworkConverter;
import ru.otus.hw.school.converters.homeworkinfo.HomeworkInfoToHomeworkInfoDtoConverter;
import ru.otus.hw.school.converters.student.StudentToStudentDtoConverter;
import ru.otus.hw.school.converters.teacher.TeacherDtoToTeacherConverter;
import ru.otus.hw.school.converters.teacher.TeacherToTeacherDtoConverter;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.services.homeworkstatus.HomeworkStatusService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StudentToStudentDtoConverter());
        modelMapper.addConverter(new StudentToStudentDtoConverter());
        modelMapper.addConverter(new TeacherToTeacherDtoConverter());
        modelMapper.addConverter(new TeacherDtoToTeacherConverter());
        modelMapper.addConverter(new GroupToGroupDtoConverter());
        modelMapper.addConverter(new GroupDtoToGroupConverter());
        modelMapper.addConverter(new CourseDtoToCourseConverter());
        modelMapper.addConverter(new CourseToCourseDtoConverter());
        modelMapper.addConverter(new HomeworkInfoDtoToHomeworkConverter());
        modelMapper.addConverter(new HomeworkInfoToHomeworkInfoDtoConverter());
        modelMapper.addConverter(new HomeworkDtoToHomeworkConverter());
        modelMapper.addConverter(new HomeworkToHomeworkDtoConverter());
        modelMapper.addConverter(new HomeworkHistoryToHomeworkHistoryDtoConverter());

        return modelMapper;
    }

    @Bean
    public Map<HomeworkStatus, HomeworkStatusService> homeworkStatusServices(List<HomeworkStatusService> statusServices) {
        return statusServices.stream()
                .collect(Collectors.toMap(HomeworkStatusService::getProcessingStatus, Function.identity()));
    }
}
