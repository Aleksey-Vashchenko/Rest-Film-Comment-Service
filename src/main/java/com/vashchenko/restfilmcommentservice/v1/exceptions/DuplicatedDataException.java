package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class DuplicatedDataException extends RuntimeException{
    public DuplicatedDataException(String message) {
        super(message);
    }
}
