package ru.otus.hw11.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("books")
public class Book {

    @Id
    private long id;

    @Column("title")
    private String title;

    @Column("author_id")
    private Long authorId;

    @Column("genre_id")
    private Long genreId;

    @Transient
    private Author author;

    @Transient
    private Genre genre;
}
