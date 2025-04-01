package ru.otus.hw.school.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.school.models.enums.GroupStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class GroupDto {
    private UUID id;

    private String groupName;

    private LocalDate startDate;

    private LocalDate endDate;

    private GroupStatus status;

    private Instant createdAt;

    private Instant updatedAt;

    private List<StudentDto> students;

    private CourseDto course;

}
