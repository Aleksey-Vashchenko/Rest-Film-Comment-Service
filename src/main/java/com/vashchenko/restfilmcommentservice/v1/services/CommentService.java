package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.repositories.CommentRepository;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
}
