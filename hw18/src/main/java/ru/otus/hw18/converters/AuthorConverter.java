package ru.otus.hw18.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw18.dto.AuthorDto;
import ru.otus.hw18.models.Author;

@Component
public class AuthorConverter {

    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(
                author.getId(),
                author.getFullName());
    }

    public AuthorDto authorToAuthorDto(Author author) {
        return new AuthorDto()
                .setId(author.getId())
                .setFullName(author.getFullName());
    }

    public Author authorDtoToAuthor(AuthorDto author) {
        return new Author()
                .setId(author.getId())
                .setFullName(author.getFullName());
    }
}
