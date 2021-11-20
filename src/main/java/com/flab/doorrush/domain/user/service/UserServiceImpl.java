package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.dto.UserDto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 회원 insert 
    @Override
    public void joinUser(UserDto userDto) {
        userMapper.joinUser(userDto);
    }

    // 회원가입 시 id 중복 확인
    @Override
    public boolean getUserById(String userId) throws Exception {
        Optional<UserDto> userDto = userMapper.getUserById(userId);
        boolean checkId = false;
        if (! userDto.isEmpty()) { // 아이디가 존재하면
            checkId = false;
        }else{ // 존재하지 않으면
            checkId = true;
        }
        return checkId;
    }

}
