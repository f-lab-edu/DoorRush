package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.dto.UserDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void mapperTest() {

        String userId = "testID1234";
        String password = "testPW";
        String name = "Yeonjae";
        String phoneNo = "01022223333";
        String addr = "aaa";
        String email = "yj@naver.com";

        UserDto user = new UserDto(userId, password, name, phoneNo, addr, email);
        Optional<UserDto> originUser = userMapper.getUserById(userId);
        if(originUser.isEmpty()){
            userMapper.joinUser(user);
        }else{
            System.out.println("이미 가입된 아이디입니다.");
        }

    }

}