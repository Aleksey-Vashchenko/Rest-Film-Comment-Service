package com.vashchenko.restfilmcommentservice.v1.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super("{\"error\": \"Данного комментария не существует\"}");
    }
}
