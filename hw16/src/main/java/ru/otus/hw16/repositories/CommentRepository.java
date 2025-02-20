package ru.otus.hw16.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw16.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(long bookId);
}
