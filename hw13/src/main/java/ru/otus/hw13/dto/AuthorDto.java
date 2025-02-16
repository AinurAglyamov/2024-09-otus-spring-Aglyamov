package ru.otus.hw13.dto;

import lombok.Data;
import ru.otus.hw13.models.Book;

import java.util.List;

@Data
public class AuthorDto {

    private long id;

    private String fullName;

    private List<Book> books;
}
