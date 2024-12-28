package ru.otus.hw09.dto;

import ru.otus.hw09.models.Book;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {

    private long id;

    private String text;

    private LocalDate commentDate;

    private Book book;
}
