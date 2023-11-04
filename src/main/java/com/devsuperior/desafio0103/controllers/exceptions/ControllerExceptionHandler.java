package com.devsuperior.desafio0103.controllers.exceptions;

import com.devsuperior.desafio0103.services.exceptions.DatabaseExcepion;
import com.devsuperior.desafio0103.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomError customError = new CustomError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status.value()).body(customError);
    }

    @ExceptionHandler(DatabaseExcepion.class)
    public ResponseEntity<CustomError> database(DatabaseExcepion e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError customError = new CustomError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status.value()).body(customError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError customError = new ValidationError(
                Instant.now(),
                status.value(),
                "Invalid data",
                request.getRequestURI()
        );

        e.getFieldErrors().forEach(
                fieldError -> customError.addError(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.status(status.value()).body(customError);
    }
}
