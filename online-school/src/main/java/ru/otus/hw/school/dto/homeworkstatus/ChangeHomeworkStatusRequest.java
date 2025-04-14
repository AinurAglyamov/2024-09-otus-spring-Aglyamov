package ru.otus.hw.school.dto.homeworkstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeHomeworkStatusRequest {

    private UUID homeworkId;

    private UUID teacherId;

    private String message;

    private Integer mark;
}
