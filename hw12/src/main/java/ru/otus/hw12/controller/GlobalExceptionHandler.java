package ru.otus.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw12.exceptions.EntityNotFoundException;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handeNotFoundException(EntityNotFoundException ex) {
        return new ModelAndView("customError",
                "errorText", "Person not found");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handeException(Exception ex) {
        return new ModelAndView("customError",
                "errorText", ex.getMessage());
    }

}
