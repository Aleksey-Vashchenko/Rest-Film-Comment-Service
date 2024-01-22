package com.vashchenko.restfilmcommentservice.v1.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.CommentJsonViews;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @JsonView(CommentJsonViews.DefaultView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(CommentJsonViews.DefaultView.class)
    private String description;

    @ManyToOne
    @JsonView(CommentJsonViews.FilmCommentView.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonView(CommentJsonViews.UserCommentView.class)
    @JoinColumn(name = "film_id")
    private Film film;

    @Min(1)
    @Max(10)
    @JsonView(CommentJsonViews.DefaultView.class)
    private Integer mark;
}
