package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dto.UserDto;
import java.util.List;

public interface UserService {

    void joinUser(UserDto userDto) throws Exception;

    List<UserDto> selectTest();
}
