package ru.otus.hw18.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw18.dto.BookDto;
import ru.otus.hw18.exceptions.EntityNotFoundException;
import ru.otus.hw18.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> all = bookService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable long id) {
        BookDto book = bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        return ResponseEntity.ok(book);
    }

    @PostMapping("/api/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto book) {
        return ResponseEntity.ok(bookService.save(book));
    }

    @PutMapping("/api/books")
    public ResponseEntity<BookDto> editBook(@RequestBody BookDto book) {
        return ResponseEntity.ok(bookService.save(book));
    }

    @DeleteMapping("/api/books")
    public ResponseEntity<Void> delete(@RequestParam long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
