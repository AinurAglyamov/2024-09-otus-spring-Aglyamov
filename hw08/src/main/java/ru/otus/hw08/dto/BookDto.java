package ru.otus.hw08.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class BookDto {

    private BigInteger id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;
}
