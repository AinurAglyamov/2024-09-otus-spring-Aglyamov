package ru.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.models.Comment;

import java.math.BigInteger;

public interface CommentRepository extends MongoRepository<Comment, BigInteger> {
}
