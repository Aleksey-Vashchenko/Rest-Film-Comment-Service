package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.FilmJsonViews;
import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.services.FilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/v1/films/*")
public class FilmController {
    @Autowired
    FilmService filmService;

    @GetMapping("/{filmId}")
    @JsonView(FilmJsonViews.DefaultView.class)
    public Film findFilmById(@PathVariable("filmId") Long filmId) {
        return filmService.findById(filmId);
    }

    @GetMapping()
    @JsonView(FilmJsonViews.DefaultView.class)
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1", name = "page") @Min(1) int page,
                                                       @RequestParam(defaultValue = "1",name = "size") @Min(1) int size) {
        Map<String, Object> response = new HashMap<>();
        response.put("films",filmService.findAll(page-1,size));
        response.put("totalPages",filmService.countPages(size));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @PostMapping
    public ResponseEntity addFilm(@RequestBody Film film){
        filmService.create(film);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/films/"+film.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @DeleteMapping("/{filmId}")
    public ResponseEntity deleteFilm(@PathVariable("id") Long filmId){
        filmService.deleteFilmById(filmId);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @PutMapping("/{filmId}")
    public ResponseEntity update(@Valid @RequestBody Film film) {
        filmService.update(film);
        return ResponseEntity.ok().build();
    }
}
