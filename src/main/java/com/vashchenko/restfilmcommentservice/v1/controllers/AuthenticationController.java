package com.vashchenko.restfilmcommentservice.v1.controllers;

import com.vashchenko.restfilmcommentservice.v1.entities.Requests.SignInRequest;
import com.vashchenko.restfilmcommentservice.v1.entities.Requests.SignUpRequest;
import com.vashchenko.restfilmcommentservice.v1.entities.Responces.JwtAuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    /*@PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }*/
}
