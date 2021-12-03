package com.flab.doorrush.domain.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.doorrush.domain.user.domain.User;
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
        String userId = "testID1234";
        String password = "testPW";
        String name = "yeonjae";
        String phoneNo = "0102222333";
        String defaultAddress = "aaa";
        String email = "yj@naver.com";

        // When
        User user = new User(userId, password, name, phoneNo, defaultAddress, email);
        int insertResult = userMapper.insertUser(user);

        // Then
        assertThat(insertResult).isEqualTo(1);

    }


    @Test
    public void getUserByIdTest() {
        // Given
        String id = "testID1";

        // When
        Optional<User> user = userMapper.getUserById(id);

        // Then
        if (user.isPresent()) {
            assertThat(user.get().getName()).isEqualTo("11");
        } else {
            System.out.println("not found");
        }
    }
}