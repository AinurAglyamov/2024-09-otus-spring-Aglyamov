package ru.otus.hw10.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.hw10.models.Book;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private long id;

    private String fullName;
}
