package com.vashchenko.restfilmcommentservice.v1.controllers;


import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/v1/users/*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        //log.info("POST / user / {}", user.getLogin());
        userService.create(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
       // log.info("PUT / user / {}", user.getLogin());
        userService.update(user);
        return user;
    }

    @GetMapping
    public List<User> findAll() {
       // log.info("GET / users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") int id) {
        //log.info("GET / users / {}", id);
        return userService.findUserById(id);
    }

}
