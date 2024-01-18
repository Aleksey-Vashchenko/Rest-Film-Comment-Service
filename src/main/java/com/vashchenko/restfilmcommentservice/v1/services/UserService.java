package com.vashchenko.restfilmcommentservice.v1.services;

import com.vashchenko.restfilmcommentservice.v1.entities.User;
import com.vashchenko.restfilmcommentservice.v1.entities.dto.UserDTO;
import com.vashchenko.restfilmcommentservice.v1.exceptions.BadRequestException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.DuplicatedDataException;
import com.vashchenko.restfilmcommentservice.v1.exceptions.UserNotFoundException;
import com.vashchenko.restfilmcommentservice.v1.mappers.UserMapper;
import com.vashchenko.restfilmcommentservice.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User create(User user){
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"login\"," +
                                                    "\"message\": \"Пользователь с данным логином уже существует\"}");
        }
        if (userRepository.findByMail(user.getMail()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"mail\"," +
                    "\"message\": \"Пользователь с данной почтой  уже существует\"}");
        }
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new DuplicatedDataException("{\"data\": \"phone\"," +
                    "\"message\": \"Пользователь с данным номером уже существует\"}");
        }
        return userRepository.save(user);
    }

    public List<User> findAll(int page, int size,String search){
        if(size==0){
            if (search.isEmpty()){
                return userRepository.findAll();
            }
            else {
                return userRepository.findByNameContaining(search);
            }
        }
        else {
            if (search.isEmpty()){
                PageRequest pageRequest = PageRequest.of(page,size);
                Page<User> result = userRepository.findAllBy(pageRequest);
                List<User> users = result.getContent();
                return users;
            }
            else {
                PageRequest pageRequest = PageRequest.of(page,size);
                Page<User> result = userRepository.findAllByNameContaining(search,pageRequest);
                List<User> users = result.getContent();
                return users;
            }
        }
    }

    public User update(Long id, User updatedUser){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException();
        }
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    public User findUserById(long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException();
    }

    public User findUserByLogin(String login){
        Optional<User> user = userRepository.findByLogin(login);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException();
    }

    public void deleteUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public Integer countPages(int size){
        if (size==0){
            return 1;
        }
          return userRepository.countPages(size);
    }
}
