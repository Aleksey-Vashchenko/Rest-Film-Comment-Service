package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException(String message) {
        super(message);
    }
}
