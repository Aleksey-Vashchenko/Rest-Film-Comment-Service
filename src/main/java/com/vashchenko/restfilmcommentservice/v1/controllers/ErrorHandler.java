package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.vashchenko.restfilmcommentservice.v1.exceptions.DuplicatedDataException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.FilmNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<String> filmNotFoundException(final FilmNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> userNotFoundException(final UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<String> duplicateDataException(final DuplicatedDataException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

}
