package ru.otus.hw.dto;

import lombok.Data;
import ru.otus.hw.models.Book;

import java.time.LocalDate;

@Data
public class CommentDto {

    private long id;

    private String text;

    private LocalDate commentDate;

    private Book book;
}
