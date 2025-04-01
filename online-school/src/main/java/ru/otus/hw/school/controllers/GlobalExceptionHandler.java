package ru.otus.hw.school.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.hw.school.exception.BusinessLogicException;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handeNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                ex.getMessage(),
                null,
                null,
                webRequest
        );
        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, webRequest);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Object> handeBusinessLogicException(BusinessLogicException ex, WebRequest webRequest) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                ex.getMessage(),
                null,
                null,
                webRequest
        );
        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, webRequest);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AuthorizationDeniedException ex, WebRequest webRequest) {
        HttpStatus statusCode = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = createProblemDetail(
                ex,
                statusCode,
                ex.getMessage(),
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
                ex.getMessage(),
                null,
                null,
                webRequest
        );

        return handleExceptionInternal(ex, problemDetail, HttpHeaders.EMPTY, statusCode, webRequest);
    }

}
