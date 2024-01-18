package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException() {
        super("{\"error\": \"Данного фильма не существует\"}");
    }
}
