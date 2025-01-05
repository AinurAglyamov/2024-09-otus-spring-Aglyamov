package ru.otus.hw10.dto;

import lombok.NoArgsConstructor;
import ru.otus.hw10.models.Book;
import lombok.Data;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String text;

    private LocalDate commentDate;
}
