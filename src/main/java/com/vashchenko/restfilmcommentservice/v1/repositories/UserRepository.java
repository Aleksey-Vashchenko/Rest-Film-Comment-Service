package com.vashchenko.restfilmcommentservice.v1.repositories;

import com.vashchenko.restfilmcommentservice.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserRepository extends CrudRepository<User,Long> {

    public Optional<User> findByLogin(String login);
    public Optional<User> findByMail(String mail);
    public Optional<User> findByPhone(String phone);

    public List<User> findAll();

    public Page<User> findAllBy(PageRequest pageRequest);

    @Query("select ceil(count(*) / :size) from User ")
    Integer countPages(@Param("size") int size);

}
