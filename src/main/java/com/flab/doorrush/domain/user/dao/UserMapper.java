package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  int insertUser(User user);

  Optional<User> selectUserById(String id);

  int getCountById(String id);

}

