package ru.otus.hw10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.hw10.exceptions.EntityNotFoundException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handeNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                "Объект не найден",
                null,
                null,
                webRequest
        );
        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handeException(Exception ex, WebRequest webRequest) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                "Ошибка при обработке запроса",
                null,
                null,
                webRequest
        );

        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, webRequest);
    }

}
