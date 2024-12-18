package ru.otus.hw08.dto;

import lombok.Data;
import ru.otus.hw08.models.Book;

import java.math.BigInteger;

@Data
public class CommentDto {

    private BigInteger id;

    private String text;

    private Book book;
}
