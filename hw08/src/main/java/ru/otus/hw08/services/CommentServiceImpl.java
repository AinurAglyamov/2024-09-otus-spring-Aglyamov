package ru.otus.hw08.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw08.converters.CommentConverter;
import ru.otus.hw08.dto.CommentDto;
import ru.otus.hw08.exceptions.EntityNotFoundException;
import ru.otus.hw08.models.Book;
import ru.otus.hw08.models.Comment;
import ru.otus.hw08.repositories.BookRepository;
import ru.otus.hw08.repositories.CommentRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentConverter commentConverter;

    @Override
    public List<CommentDto> findAllByBookId(BigInteger bookId) {
        return bookRepository.findById(bookId)
                .map(Book::getComments)
                .map(comments -> comments.stream()
                        .map(commentConverter::commentToCommentDto)
                        .toList()
                )
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
    }

    @Override
    public Optional<CommentDto> findById(BigInteger id) {
        return commentRepository.findById(id)
                .map(commentConverter::commentToCommentDto);
    }

    @Override
    public CommentDto insert(String text, BigInteger bookId) {
        var comment = new Comment();
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        comment.setText(text);
        comment.setBook(book);

        List<Comment> comments = Optional.ofNullable(book.getComments()).orElse(new ArrayList<>());
        comments.add(comment);

        Comment insertedComment = commentRepository.save(comment);
        bookRepository.save(book);

        return commentConverter.commentToCommentDto(insertedComment);
    }

    @Override
    public CommentDto update(BigInteger id, String text) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setText(text);

        Comment updatedComment = commentRepository.save(comment);

        return commentConverter.commentToCommentDto(updatedComment);
    }

    @Override
    public void deleteById(BigInteger id) {
        commentRepository.deleteById(id);
    }
}
