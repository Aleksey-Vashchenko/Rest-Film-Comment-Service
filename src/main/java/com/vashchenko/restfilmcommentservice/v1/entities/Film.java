package com.vashchenko.restfilmcommentservice.v1.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.FilmJsonViews;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "comments")
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(FilmJsonViews.DefaultView.class)
    private Long id;

    @JsonView(FilmJsonViews.DefaultView.class)
    private String name;

    @JsonView(FilmJsonViews.DefaultView.class)
    private String directors;

    @JsonView(FilmJsonViews.DefaultView.class)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "films_to_genres",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonView(FilmJsonViews.DefaultView.class)
    private Set<Genre> genres;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "comments",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Comment> comments;

}
