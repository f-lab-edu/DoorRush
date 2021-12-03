package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicateUserIdException;
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
    public void getUserById_success_test() {
        // Given , When
        UserDto user = userService.getUserById("test1");

        // Then
        assertThat(user.getId()).isEqualTo("test1");
        assertThat(user.getName()).isEqualTo("11");
    }

    @Test
    public void getUserById_fail_test() {
        // Given, When, Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("test1234234"));
    }

    @Test
    public void isDuplicatedId_fail_test() {
        // Given, When, Then
        assertThrows(DuplicateUserIdException.class, () -> userService.joinUser(User.builder()
            .id("test1")
            .password("1234")
            .build()));
    }

    @Test
    public void joinUser_success_test() {
        // Given
        userService.joinUser(User.builder()
            .id("testId")
            .password("aaasssddd")
            .email("aaasssddd@naver.com")
            .name("aaasssddd")
            .defaultAddress("경기도")
            .phoneNumber("01077778888")
            .build());

        // When
        UserDto user = userService.getUserById("testId");

        // Then
        assertThat(user.getId()).isEqualTo("testId");
        assertThat(user.getName()).isEqualTo("aaasssddd");
    }
}