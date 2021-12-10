package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void insertUser(User user);

    Optional<User> getUserById(String id);

    int countById(String id);

}

