package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import com.vashchenko.restfilmcommentservice.v1.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    public Comment create(@Valid @RequestBody Comment comment) {
        //log.info("POST / user / {}", user.getLogin());
        System.out.println(comment);
        return new Comment();
    }
}
