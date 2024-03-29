package com.vashchenko.restfilmcommentservice.v1.repositories;

import com.vashchenko.restfilmcommentservice.v1.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

    boolean existsByName(String name);
}
