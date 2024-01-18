package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.vashchenko.restfilmcommentservice.v1.entities.Genre;
import com.vashchenko.restfilmcommentservice.v1.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/v1/genres/*")
public class GenresController {
    @Autowired
    GenreService genreService;

    @GetMapping
    public List<Genre> getGenres(){
        return genreService.findAll();
    }

    @PostMapping
    public ResponseEntity addGenre(@RequestBody Genre genre){
        genreService.create(genre);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/genres/"+genre.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity deleteGenre(@PathVariable("id") Long genreId){
        //log.info("GET / users / {}", id);
        genreService.deleteGenreById(genreId);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{genreId}")
    public Genre update(@Valid @RequestBody Genre genre) {
        // log.info("PATCH / user / {}", user.getLogin());
        genreService.update(genre);
        return genre;
    }

}
