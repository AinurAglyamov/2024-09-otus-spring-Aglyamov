package ru.otus.hw17.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String text;

    private LocalDate commentDate;
}
