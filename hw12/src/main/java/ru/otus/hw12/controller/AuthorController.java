package ru.otus.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw12.dto.AuthorDto;
import ru.otus.hw12.services.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String listPage(Model model) {
        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "book";
    }
}
