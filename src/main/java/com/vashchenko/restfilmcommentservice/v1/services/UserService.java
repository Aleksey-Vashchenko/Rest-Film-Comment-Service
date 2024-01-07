package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.exceptions.DuplicatedDataException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.UserNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User create(User user){
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"login\"" +
                                                    "\"message\": \"Пользователь с данным логином уже существует\"}");
        }
        if (userRepository.findByMail(user.getMail()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"mail\"" +
                    "\"message\": \"Пользователь с данной почтой  уже существует\"}");
        }
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"phone\"" +
                    "\"message\": \"Пользователь с данным номером уже существует\"}");
        }
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User update(User user){
        if (userRepository.findById(user.getId()).isPresent()){
            throw new UserNotFoundException("Данного пользователя не существует");
        }
        return userRepository.save(user);
    }

    public User findUserById(long id){
        User userFromDatabase = userRepository.findById(id).get();
        if (userFromDatabase==null){
            throw new UserNotFoundException("Данного пользователя не существует");
        }
        return userFromDatabase;
    }
}
