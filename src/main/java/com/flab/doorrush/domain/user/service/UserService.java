package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dto.UserDto;

public interface UserService {

    void joinUser(UserDto userDto) throws Exception;

    boolean getUserById(String userId) throws Exception;

}
