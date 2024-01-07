package com.vashchenko.restfilmcommentservice.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String directors;

    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "films_to_genres",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )

    private Set<Genre> genres;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "comments",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Comment> comments;

}
