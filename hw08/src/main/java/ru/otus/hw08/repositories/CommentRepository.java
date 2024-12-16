package ru.otus.hw08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw08.models.Comment;

import java.math.BigInteger;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, BigInteger> {

    Optional<Comment> findById(BigInteger id);

    Comment save(Comment comment);

    void deleteById(BigInteger id);
}
