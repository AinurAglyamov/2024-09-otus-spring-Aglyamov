package ru.otus.hw17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String booksPage() {
        return "books";
    }

    @GetMapping("/create")
    public String createBookPage() {
        return "create";
    }

    @GetMapping("/edit")
    public String editBookPage() {
        return "edit";
    }
}
