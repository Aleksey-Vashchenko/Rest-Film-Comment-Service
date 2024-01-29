package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.CommentJsonViews;
import com.vashchenko.restfilmcommentservice.v1.configs.security.Roles;
import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.services.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/films/{filmId}/comments")
public class CommentFilmController {
    @Autowired
    CommentService commentService;

    @GetMapping
    @JsonView(CommentJsonViews.FilmCommentView.class)
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1", name = "page") @Min(1) int page,
                                                       @RequestParam(defaultValue = "1",name = "size") @Min(1) int size,
                                                       @PathVariable("filmId") Long filmId) {
        Map<String, Object> response = new HashMap<>();
        response.put("comments",commentService.findAllByFilm(page-1,size,filmId));
        response.put("totalPages",commentService.countPagesByFilm(size,filmId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity create(@PathVariable("filmId") Long filmId, @Valid @RequestBody Comment comment, Principal principal) {
        commentService.create(comment,filmId,principal.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/films/"+filmId+"/comments/"+comment.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication){
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.getAuthority()))){
            commentService.deleteCommentById(commentId);
        }
        else {
            commentService.deleteCommentById(commentId,authentication.getName());
        }
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity update(@Valid @RequestBody Comment comment, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.getAuthority()))){
            commentService.update(comment);
        }
        else {
            commentService.update(comment, authentication.getName());
        }

        return ResponseEntity.ok().build();
    }
}
