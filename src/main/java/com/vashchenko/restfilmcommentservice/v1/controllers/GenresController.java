package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.GenreJsonViews;
import com.vashchenko.restfilmcommentservice.v1.entities.Genre;
import com.vashchenko.restfilmcommentservice.v1.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/v1/genres/*")
public class GenresController {
    @Autowired
    GenreService genreService;

    @GetMapping
    @JsonView(GenreJsonViews.DefaultView.class)
    public List<Genre> getGenres(){
        return genreService.findAll();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @PostMapping
    public ResponseEntity addGenre(@RequestBody Genre genre){
        genreService.create(genre);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/genres/"+genre.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @DeleteMapping("/{genreId}")
    public ResponseEntity deleteGenre(@PathVariable("genreId") Long genreId){
        genreService.deleteGenreById(genreId);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @PutMapping("/{genreId}")
    public ResponseEntity update(@PathVariable("genreId") Long genreId,@Valid @RequestBody Genre genre) {
        genre.setId(genreId);
        genreService.update(genre);
        return ResponseEntity.ok().build();
    }

}
