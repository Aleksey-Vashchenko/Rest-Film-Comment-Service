package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("{\"error\": \"Данного пользователя не существует\"}");
    }
}
