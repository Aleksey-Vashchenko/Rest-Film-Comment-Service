package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
