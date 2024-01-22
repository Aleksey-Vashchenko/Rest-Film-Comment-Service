package com.vashchenko.restfilmcommentservice.v1.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vashchenko.restfilmcommentservice.v1.configs.FilmJsonViews;
import com.vashchenko.restfilmcommentservice.v1.configs.GenreJsonViews;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@ToString(exclude = "films")
@EqualsAndHashCode(exclude = "films")
@Table(name = "genres")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(GenreJsonViews.DefaultView.class)
    private Long id;

    @NotNull
    @JsonView(GenreJsonViews.DefaultView.class)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Film> films;
}
