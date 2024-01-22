package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.CommentJsonViews;
import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import com.vashchenko.restfilmcommentservice.v1.services.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/users/{userId}/comments")
public class CommentUserController {
    @Autowired
    CommentService commentService;

    @GetMapping
    @JsonView(CommentJsonViews.UserCommentView.class)
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1", name = "page") @Min(1) int page,
                                                       @RequestParam(defaultValue = "1",name = "size") @Min(1) int size,
                                                       @PathVariable("userId") Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("comments",commentService.findAllByUser(page-1,size,userId));
        response.put("totalPages",commentService.countPagesByUser(size,userId));
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteCommentById(commentId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity update(@Valid @RequestBody Comment comment) {
        commentService.update(comment);
        return ResponseEntity.ok().build();
    }
}
