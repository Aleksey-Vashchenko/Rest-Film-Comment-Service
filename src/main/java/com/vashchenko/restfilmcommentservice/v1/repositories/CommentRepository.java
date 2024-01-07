package com.vashchenko.restfilmcommentservice.v1.repositories;

import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
}
