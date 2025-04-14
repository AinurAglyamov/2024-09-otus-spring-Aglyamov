package ru.otus.hw.school.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.school.models.enums.HomeworkStatus;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class HomeworkHistoryDto {

    private UUID id;

    private StudentDto student;

    private TeacherDto teacher;

    private HomeworkStatus status;

    private String message;

    private Instant createdAt;

    private Instant updatedAt;
}
