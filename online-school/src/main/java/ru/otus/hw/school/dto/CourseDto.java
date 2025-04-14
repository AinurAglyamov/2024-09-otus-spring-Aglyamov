package ru.otus.hw.school.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class CourseDto {

    private UUID id;

    private String courseName;

    private BigDecimal cost;

    private String description;

    private List<TeacherDto> teachers;

    private Instant createdAt;

    private Instant updatedAt;
}
