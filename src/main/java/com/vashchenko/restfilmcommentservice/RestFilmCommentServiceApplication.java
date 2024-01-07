package com.vashchenko.restfilmcommentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.vashchenko.restfilmcommentservice.v1"})
@EntityScan(basePackages = {"com.vashchenko.restfilmcommentservice"})
@EnableJpaRepositories(basePackages = "com.vashchenko.restfilmcommentservice.v1")
public class RestFilmCommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestFilmCommentServiceApplication.class, args);
    }

}
