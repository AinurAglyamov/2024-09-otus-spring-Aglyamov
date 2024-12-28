package ru.otus.hw09.dto;

import ru.otus.hw09.models.Book;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {

    private long id;

    private String fullName;

    private List<Book> books;
}
