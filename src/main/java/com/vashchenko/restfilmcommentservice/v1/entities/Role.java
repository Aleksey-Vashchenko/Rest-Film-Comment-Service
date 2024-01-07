package com.vashchenko.restfilmcommentservice.v1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString(exclude = "userSet")
@EqualsAndHashCode(exclude = "userSet")
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> userSet;
}
