package ru.otus.hw18.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw18.converters.CommentConverter;
import ru.otus.hw18.dto.CommentDto;
import ru.otus.hw18.exceptions.EntityNotFoundException;
import ru.otus.hw18.models.Comment;
import ru.otus.hw18.repositories.BookRepository;
import ru.otus.hw18.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentConverter commentConverter;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBookId(long bookId) {
        return commentRepository.findByBookId(bookId)
                .stream()
                .map(commentConverter::commentToCommentDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id)
                .map(commentConverter::commentToCommentDto);
    }

    @Override
    @Transactional
    public CommentDto insert(String text, long bookId) {
        var comment = new Comment();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        comment.setText(text);
        comment.setBook(book);

        Comment insertedComment = commentRepository.save(comment);

        return commentConverter.commentToCommentDto(insertedComment);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setText(text);

        Comment updatedComment = commentRepository.save(comment);

        return commentConverter.commentToCommentDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
