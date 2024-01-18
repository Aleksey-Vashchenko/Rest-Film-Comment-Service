package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.entities.Genre;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.exceptions.DuplicatedDataException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.GenreNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.UserNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.repositories.GenreRepository;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> findAll(){
        return  genreRepository.findAll();
    }

    public void create(Genre genre){
        if(genreRepository.existsByName(genre.getName())){
            throw new DuplicatedDataException("{\"data\": \"name\"," +
                    "\"message\": \"Жанр с таким наименованием существует\"}");
        }
        else {
            genreRepository.save(genre);
        }
    }

    public void deleteGenreById(Long id){
        Optional<Genre> genre = genreRepository.findById(id);
        if(!genre.isPresent()){
            throw new GenreNotFoundException();
        }
        genreRepository.deleteById(id);
    }

    public Genre update(Genre genre){
        if (!genreRepository.findById(genre.getId()).isPresent()){
            throw new GenreNotFoundException();
        }
        return genreRepository.save(genre);
    }
}
