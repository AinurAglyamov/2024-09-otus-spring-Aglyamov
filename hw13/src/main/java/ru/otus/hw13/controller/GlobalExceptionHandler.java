package ru.otus.hw13.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw13.exceptions.EntityNotFoundException;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handeNotFoundException(EntityNotFoundException ex) {
        return new ModelAndView("customError",
                "errorText", "Person not found");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handeException(Exception ex) {
        log.info(ex.getMessage(), ex);
        return new ModelAndView("customError",
                "errorText", ex.getMessage());
    }

}
