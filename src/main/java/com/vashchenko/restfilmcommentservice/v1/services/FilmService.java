package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.exceptions.DuplicatedDataException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.FilmNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.UserNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.repositories.FilmRepository;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    FilmRepository filmRepository;

    public List<Film> findAll(int page, int size){
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Film> result = filmRepository.findAllBy(pageRequest);
            List<Film> films = result.getContent();
            return films;
    }

    public Integer countPages(int size){
        return filmRepository.countPages(size);
    }

    public Film create(Film film){
        return filmRepository.save(film);
    }

    public Film update(Film film){
        if (!filmRepository.findById(film.getId()).isPresent()){
            throw new FilmNotFoundException();
        }
        return filmRepository.save(film);
    }

    public void deleteFilmById(Long id){
        Optional<Film> user = filmRepository.findById(id);
        if(!user.isPresent()){
            throw new FilmNotFoundException();
        }
        filmRepository.deleteById(id);
    }

    public Film findById(Long id){
         return filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException());
    }
}
