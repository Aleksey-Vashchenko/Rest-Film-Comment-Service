package com.vashchenko.restfilmcommentservice.v1.controllers;


import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/v1/users/*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody User user) {
        //log.info("POST / user / {}", user.getLogin());
        user.setEnabled(true);
        userService.create(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/users/"+user.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") Long userId ,@Valid @RequestBody User user) {
       // log.info("PATCH / user / {}", user.getLogin());
        return userService.update(userId,user);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                       @RequestParam(defaultValue = "0") @Min(0) int size) {
       // log.info("GET / users");
        Map<String, Object> response = new HashMap<>();
        response.put("users",userService.findAll(page-1,size));
        response.put("totalPages",userService.countPages(size));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        //log.info("GET / users / {}", login);
        return userService.findUserById(id);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity deleteUserById(@PathVariable("userId") Long userId) {
        //log.info("GET / users / {}", id);
        userService.deleteUserById(userId);
        return ResponseEntity.status(204).build();
    }

}
