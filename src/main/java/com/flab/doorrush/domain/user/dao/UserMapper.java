package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.dto.UserDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void joinUser(UserDto userDto);

    List<UserDto> selectTest();
}

