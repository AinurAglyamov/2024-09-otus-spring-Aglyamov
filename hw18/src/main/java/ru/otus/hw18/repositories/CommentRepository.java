package ru.otus.hw18.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw18.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(long bookId);
}
