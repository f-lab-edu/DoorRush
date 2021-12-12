package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.doorrush.domain.user.common.LoginEnum;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;

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

  @Test
  public void loginSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test1", "test1pw");

    // When
    LoginEnum result = userService.login(loginDto, session);

    // Then
    MatcherAssert.assertThat(HttpStatus.OK, is(result.getValue()));
    //MatcherAssert.assertThat("fail", is(incorrectInput));
  }

  @Test
  public void loginFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test1", "test222222222pw");

    // When
    LoginEnum result = userService.login(loginDto, session);

    // Then
    MatcherAssert.assertThat(HttpStatus.NOT_FOUND, is(result.getValue()));
  }

  @Test
  public void logoutSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test1", "test1pw");
    userService.login(loginDto, session);

    // When
    LoginEnum result = userService.logout(session);
    // Then
    MatcherAssert.assertThat(HttpStatus.OK, is(result.getValue()));
  }

  @Test
  public void logoutFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    session.setAttribute("login","no");

    // When
    LoginEnum result = userService.logout(session);
    // Then
    MatcherAssert.assertThat(HttpStatus.NOT_FOUND, is(result.getValue()));
  }
}