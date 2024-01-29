package com.vashchenko.restfilmcommentservice.v1.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.UserJsonViews;
import com.vashchenko.restfilmcommentservice.v1.configs.security.Roles;
import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/v1/users/*")
public class UserController {
    @Autowired
    UserService userService;

    @Secured("ROLE_MANAGER")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody User user) {
        user.setEnabled(true);
        userService.create(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/users/"+user.getId());
        return ResponseEntity.status(201)
                .headers(headers)
                .build();
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_MANAGER'})")
    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable("userId") Long userId ,@Valid @RequestBody User user) {
        userService.update(userId,user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @JsonView(UserJsonViews.DefaultView.class)
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "1", name = "page") @Min(1) int page,
                                                       @RequestParam(defaultValue = "1",name = "size") @Min(1) int size,
                                                       @RequestParam(defaultValue = "") String search) {
        Map<String, Object> response = new HashMap<>();
        response.put("users",userService.findAll(page-1,size,search));
        response.put("totalPages",userService.countPages(size));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public MappingJacksonValue findUserById(@PathVariable("id") Long id, Authentication authentication) {
        User user = userService.findUserById(id);
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getAuthorities().stream().anyMatch(a -> "ADMIN".equals(a.getAuthority()))) {
            return adminView(user);
        } else {
            return userView(user);
        }
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity deleteUserById(@PathVariable("userId") Long userId, Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ROLE_MANAGER.getAuthority()))){
        }
        else {
            userService.deleteUserById(userId);
        }
        return ResponseEntity.status(204).build();
    }

    private MappingJacksonValue adminView(User user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setSerializationView(UserJsonViews.AdminView.class);
        return mappingJacksonValue;
    }

    private MappingJacksonValue userView(User user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setSerializationView(UserJsonViews.DefaultView.class);
        return mappingJacksonValue;
    }

}
