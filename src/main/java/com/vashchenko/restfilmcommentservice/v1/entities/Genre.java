package com.vashchenko.restfilmcommentservice.v1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

@Entity
@ToString(exclude = "films")
@EqualsAndHashCode(exclude = "films")
@Table(name = "genres")
@Data
public class Genre {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Film> films;
}
