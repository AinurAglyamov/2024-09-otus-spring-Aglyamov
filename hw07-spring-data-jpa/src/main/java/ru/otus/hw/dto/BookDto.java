package ru.otus.hw.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;
}
