package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @SpringBootTest : SpringBoot 기능을 제공해주며 SpringBoot 통합테스트를 할 때 사용되는 주석
 * @SpringBootTest 어노테이션은 Spring Main Application(@SpringBootApplication)을 찾아가 하위의 모든 Bean을 Scan
 * */
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void getUserByIdSuccessTest() {
        // Given , When
        UserDto user = userService.getUserById("test1");

        // Then
        assertThat(user.getId()).isEqualTo("test1");
        assertThat(user.getName()).isEqualTo("11");
    }

    @Test
    public void getUserByIdFailTest() {
        // Given, When, Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("test1234234"));
    }

    @Test
    public void isDuplicatedIdFailTest() {
        // Given, When, Then
        assertThrows(DuplicatedUserIdException.class, () -> userService.joinUser(User.builder()
            .id("test1")
            .password("1234")
            .build()));
    }

    @Test
    public void joinUserSuccessTest() {
        // Given
        userService.joinUser(User.builder()
            .id("testId")
            .password("aaasssddd")
            .email("aaasssddd@naver.com")
            .name("aaasssddd")
            .phoneNumber("01077778888")
            .build());

        // When
        UserDto user = userService.getUserById("testId");

        // Then
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getName()).isEqualTo("aaasssddd");
    }
}