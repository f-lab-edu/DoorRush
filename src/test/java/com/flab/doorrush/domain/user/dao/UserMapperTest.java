package com.flab.doorrush.domain.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void userInsertTest() {

        // Given
        String id = "testID1234";
        String password = "testPW";
        String name = "yeonjae";
        String phoneNumber = "0102222333";
        String email = "yj@naver.com";

        // When
        int insertResult = userMapper.insertUser(User.builder()
            .id(id)
            .password(password)
            .phoneNumber(phoneNumber)
            .email(email)
            .name(name)
            .build());

        // Then
        assertThat(insertResult).isEqualTo(1);

    }


    @Test
    public void getUserByIdTest() {
        // Given
        String id = "test1";

        // When
        Optional<User> user = userMapper.getUserById(id);

        // Then
        if (user.isPresent()) {
            assertThat(user.get().getName()).isEqualTo("11");
        } else {
            throw new UserNotFoundException("회원정보가 없습니다.");
        }
    }
}