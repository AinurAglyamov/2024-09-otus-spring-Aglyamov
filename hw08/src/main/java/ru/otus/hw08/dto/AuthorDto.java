package ru.otus.hw08.dto;

import lombok.Data;
import ru.otus.hw08.models.Book;

import java.math.BigInteger;
import java.util.List;

@Data
public class AuthorDto {

    private BigInteger id;

    private String fullName;

    private List<Book> books;
}
