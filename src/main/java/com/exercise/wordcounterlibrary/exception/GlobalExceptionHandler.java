package com.exercise.wordcounterlibrary.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@PropertySource("classpath:errorMessages.properties")
public class GlobalExceptionHandler {

    private final static String ERROR = "error";

    @Value("${validation.word.reference}")
    private String validationWordReference;

    @Value("${not.found.word.reference}")
    private String notFoundWordReference;

    @Value("${empty.input.reference}")
    private String emptyInputReference;

    @ExceptionHandler(NotFoundInputException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundInputException() {
        Map<String, Object> response = new HashMap<>();

        response.put(ERROR, notFoundWordReference);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonAlphabeticInputException.class)
    public ResponseEntity<Map<String, Object>> handleNonAlphabeticInputException() {
        Map<String, Object> response = new HashMap<>();

        response.put(ERROR, validationWordReference);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyOrBlankInputException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyOrBlankInputException() {
        Map<String, Object> response = new HashMap<>();

        response.put(ERROR, emptyInputReference);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
