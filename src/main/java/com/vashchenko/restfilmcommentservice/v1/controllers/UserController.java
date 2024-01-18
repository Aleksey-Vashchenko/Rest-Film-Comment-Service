package com.vashchenko.restfilmcommentservice.v1.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.UserJsonViews;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") Long userId ,@Valid @RequestBody User user) {
       // log.info("PATCH / user / {}", user.getLogin());
        return userService.update(userId,user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                       @RequestParam(defaultValue = "0") @Min(0) int size,
                                                       @RequestParam(defaultValue = "") String search) {
       // log.info("GET / users");
        Map<String, Object> response = new HashMap<>();
        response.put("users",userService.findAll(page-1,size,search));
        response.put("totalPages",userService.countPages(size));
        return ResponseEntity.ok(response);
    }

    @JsonView(UserJsonViews.UserJsonView.class)
    @GetMapping("/{id}")
    public MappingJacksonValue findUserById(@PathVariable("id") Long id, Authentication authentication) {
        //log.info("GET / users / {}", login);
        User user = userService.findUserById(id);
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getAuthorities().stream().anyMatch(a -> "ADMIN".equals(a.getAuthority()))) {
            // Если пользователь - админ, установите AdminView
            return adminView(user);
        } else {
            // В противном случае, установите UserView
            return userView(user);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity deleteUserById(@PathVariable("userId") Long userId) {
        //log.info("GET / users / {}", id);
        userService.deleteUserById(userId);
        return ResponseEntity.status(204).build();
    }

    private MappingJacksonValue adminView(User user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setSerializationView(UserJsonViews.AdminJsonView.class);
        return mappingJacksonValue;
    }

    private MappingJacksonValue userView(User user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setSerializationView(UserJsonViews.UserJsonView.class);
        return mappingJacksonValue;
    }

}
