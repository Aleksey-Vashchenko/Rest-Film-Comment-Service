package com.vashchenko.restfilmcommentservice;

import com.vashchenko.restfilmcommentservice.v1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.vashchenko.restfilmcommentservice.v1"})
@EntityScan(basePackages = {"com.vashchenko.restfilmcommentservice"})
@EnableJpaRepositories(basePackages = "com.vashchenko.restfilmcommentservice.v1")
public class RestFilmCommentServiceApplication {

    @Autowired
    static UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(RestFilmCommentServiceApplication.class, args);
    }

}
