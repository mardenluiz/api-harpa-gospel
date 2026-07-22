package com.mardenluiz.harpa.api.web.exception.handler;


import com.mardenluiz.harpa.api.web.exception.ApiError;
import com.mardenluiz.harpa.api.domain.exception.ResourceNotFoundException;
import com.mardenluiz.harpa.api.web.exception.ValidationError;
import com.mardenluiz.harpa.api.web.exception.FieldErrorValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                                       @Nullable Object body, HttpHeaders headers,
                                                                       HttpStatusCode statusCode, WebRequest request) {

        String nameError = statusCode
                .toString()
                .split(" ")[1]
                .replace("_", " ")
                .toLowerCase();

        System.out.println(request.getContextPath());

        body = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode.value())
                .error(nameError)
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(Exception ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError error = ApiError
                .builder()
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .error(status.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationError> handleConstraintViolation(ConstraintViolationException ex,
                                                                     HttpServletRequest request) {

        List<FieldErrorValidation> fields = ex.getConstraintViolations()
                .stream()
                .map(error -> new FieldErrorValidation(
                        error.getPropertyPath().toString().split("\\.")[1],
                        error.getMessage()))
                .toList();

        ValidationError error = ValidationError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Erro de validação.")
                .path(request.getRequestURI())
                .fields(fields)
                .build();

        return ResponseEntity.badRequest().body(error);
    }
}
