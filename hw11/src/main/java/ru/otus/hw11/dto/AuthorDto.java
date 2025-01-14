package ru.otus.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private long id;

    private String fullName;
}
