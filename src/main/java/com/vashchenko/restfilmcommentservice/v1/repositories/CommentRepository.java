package com.vashchenko.restfilmcommentservice.v1.repositories;

import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
    Page<Comment> findAllBy(PageRequest pageRequest);

    Page<Comment> findAllByUser(PageRequest pageRequest,User user);
    Page<Comment> findAllByFilm(PageRequest pageRequest,Film film);

    @Query("select ceil(count(*) / :size) from Comment")
    Integer countPages(@Param("size") int size);

    @Query("select ceil(count(*) / :size) from Comment c where c.film.id=:filmId")
    Integer countPagesByFilm(@Param("size") int size,
                             @Param("filmId") long id);

    @Query("select ceil(count(*) / :size) from Comment c where c.user.id=:userId")
    Integer countPagesByUser(@Param("size") int size,
                             @Param("userId") long userId);
}
