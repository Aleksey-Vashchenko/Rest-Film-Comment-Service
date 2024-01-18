package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.vashchenko.restfilmcommentservice.v1.entities.Film;
import com.vashchenko.restfilmcommentservice.v1.services.FilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/v1/films/*")
public class FilmController {
    @Autowired
    FilmService filmService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                       @RequestParam(defaultValue = "0") @Min(0) int size) {
        // log.info("GET / films");
        Map<String, Object> response = new HashMap<>();
        response.put("films",filmService.findAll(page-1,size));
        response.put("totalPages",filmService.countPages(size));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity addFilm(@RequestBody Film film){
        //log.info("POST / films / {}", film.getId());
        filmService.create(film);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/films/"+film.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @DeleteMapping("/{filmId}")
    public ResponseEntity deleteFilm(@PathVariable("id") Long filmId){
        //log.info("GET / films / {}", id);
        filmService.deleteFilmById(filmId);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{filmId}")
    public Film update(@Valid @RequestBody Film film) {
        // log.info("PATCH / films / {}", films.getId());
        filmService.update(film);
        return film;
    }
}
