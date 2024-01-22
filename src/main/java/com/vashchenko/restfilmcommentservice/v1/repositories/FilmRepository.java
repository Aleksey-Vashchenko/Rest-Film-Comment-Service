package com.vashchenko.restfilmcommentservice.v1.repositories;

import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {
    public List<Film> findAll();

    @Transactional
    public Page<Film> findAllBy(PageRequest pageRequest);

    @Query("select ceil(count(*) / :size) from Film ")
    Integer countPages(@Param("size") int size);

}
