package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.entities.Comment;
import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.exceptions.BadRequestException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.CommentNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.repositories.CommentRepository;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    FilmService filmService;

    public List<Comment> findAll(int page, int size){
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Comment> result = commentRepository.findAllBy(pageRequest);
            List<Comment> comments = result.getContent();
            return comments;
    }

    public Integer countPages(int size){
        return commentRepository.countPages(size);
    }

    public Comment create(Comment comment, Long filmId, String name){
        User user = userService.findUserByLogin(name);
        Film film = filmService.findById(filmId);
        comment.setFilm(film);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment){
        if (!commentRepository.findById(comment.getId()).isPresent()){
            throw new CommentNotFoundException();
        }
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment, String login){
        if (!commentRepository.findById(comment.getId()).isPresent()){
            throw new CommentNotFoundException();
        }
        User user = userService.findUserByLogin(login);
        if(user.getId()==comment.getId()){
            return commentRepository.save(comment);
        }
        else throw new BadRequestException("Нет прав для редактирования данного комментария");
    }

    public void deleteCommentById(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(id);
    }

    public void deleteCommentById(Long id, String login){
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        User user = userService.findUserByLogin(login);
        if(user.getId()==comment.get().getId()){
            commentRepository.deleteById(id);
        }
        else {
            throw new BadRequestException("Нет прав для удаления данного комментария");
        }
    }

    public List<Comment> findAllByFilm(int page, int size, long filmId){
        if(size==0){
            return commentRepository.findAll();
        }
        else {
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Comment> result = commentRepository.findAllByFilm(pageRequest,
                    Film.builder().id(filmId).build());
            List<Comment> comments = result.getContent();
            return comments;
        }
    }

    public Integer countPagesByFilm(int size, Long userId){
        return commentRepository.countPagesByFilm(size,userId);
    }

    public List<Comment> findAllByUser(int page, int size, long userId){
        if(size==0){
            return commentRepository.findAll();
        }
        else {
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Comment> result = commentRepository.findAllByUser(pageRequest,
                    User.builder().id(userId).build());
            List<Comment> comments = result.getContent();
            return comments;
        }
    }

    public Integer countPagesByUser(int size, Long userId){
        return commentRepository.countPagesByUser(size,userId);
    }
}
