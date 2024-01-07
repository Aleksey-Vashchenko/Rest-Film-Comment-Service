package com.vashchenko.restfilmcommentservice.v1.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

@Entity
@ToString(exclude = "films")
@EqualsAndHashCode(exclude = "films")
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Film> films;
}
