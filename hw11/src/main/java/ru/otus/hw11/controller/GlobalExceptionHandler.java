package ru.otus.hw11.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.otus.hw11.exceptions.EntityNotFoundException;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public Mono<ResponseEntity<Object>> handleNotFoundException(EntityNotFoundException ex, ServerWebExchange exchange) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                "Объект не найден",
                null,
                null,
                exchange
        );

        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, exchange);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Object>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                "Ошибка при обработке запроса",
                null,
                null,
                exchange
        );

        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, exchange);
    }
}
