package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  void insertUser(User user);

  Optional<User> selectUserById(String loginId);

  Optional<User> selectUserByUserSeq(Long userSeq);

  int updatePassword(User user);


}

