package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String message) {
        super(message);
    }
}
