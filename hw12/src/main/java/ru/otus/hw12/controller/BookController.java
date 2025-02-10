package ru.otus.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw12.dto.AuthorDto;
import ru.otus.hw12.dto.BookDto;
import ru.otus.hw12.dto.GenreDto;
import ru.otus.hw12.exceptions.EntityNotFoundException;
import ru.otus.hw12.services.AuthorService;
import ru.otus.hw12.services.BookService;
import ru.otus.hw12.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String booksPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/create")
    public String createBookPage(Model model) {
        BookDto book = new BookDto();
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "create";
    }

    @PostMapping("/create")
    public String create(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editBookPage(Model model, @RequestParam("id") long id) {
        BookDto book = bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(BookDto book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") long id) {
        bookService.deleteById(id);
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "redirect:/";
    }
}
