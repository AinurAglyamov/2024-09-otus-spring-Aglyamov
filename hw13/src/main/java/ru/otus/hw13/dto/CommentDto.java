package ru.otus.hw13.dto;

import lombok.Data;
import ru.otus.hw13.models.Book;

import java.time.LocalDate;

@Data
public class CommentDto {

    private long id;

    private String text;

    private LocalDate commentDate;

    private Book book;
}
