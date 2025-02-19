package ru.otus.hw14.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("books")
public class MongoBook {

    @Id
    private BigInteger id;

    private String title;

    @DBRef
    private MongoAuthor author;

    @DBRef
    private MongoGenre genre;
}
