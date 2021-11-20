package com.flab.doorrush.domain.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.doorrush.domain.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void mapperTest() {

        String userId = "testID";
        String password = "testPW";
        String name = "Yeonjae";
        String phoneNo = "01022223333";
        String addr = "aaa";
        String email = "yj@naver.com";

        UserDto user = new UserDto(userId, password, name, phoneNo, addr, email);
        userMapper.joinUser(user);
        assertThat(user.getUserName()).isEqualTo(name);

    }

}