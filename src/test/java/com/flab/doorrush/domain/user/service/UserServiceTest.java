package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("아이디로 사용자 정보 조회결과가 예상한 결과값과 동일하다.")
  public void getUserByIdSuccessTest() {
    // Given , When
    FindUserResponse user = userService.getUserById("test1");

    // Then
    assertThat(user.getUser().getLoginId()).isEqualTo("test1");
    assertThat(user.getUser().getName()).isEqualTo("11");
  }

  @Test
  @DisplayName("아이디로 사용자 정보 조회 후 없으면 예외를 발생시킨다.")
  public void getUserByIdFailTest() {
    // Given, When, Then
    assertThrows(UserNotFoundException.class, () -> userService.getUserById("test1234234"));
  }

  @Test
  @DisplayName("아이디 중복 테스트 중복된 아이디일 경우 예외를 발생시킨다.")
  public void isDuplicatedIdFailTest() {
    JoinUserRequest joinUserRequest = JoinUserRequest.builder()
        .loginId("test1")
        .password("password")
        .name("testName")
        .phoneNumber("01011112222")
        .email("testEmail@naver.com")
        .build();

    // Given, When, Then
    assertThrows(DuplicatedUserIdException.class, () -> userService.joinUser(joinUserRequest));
  }

  @Test
  @DisplayName("사용자 회원가입 테스트 정상적으로 회원가입된다.")
  public void joinUserSuccessTest() {
    // Given
    userService.joinUser(JoinUserRequest.builder()
        .loginId("testId")
        .password("aaasssddd")
        .email("aaasssddd@naver.com")
        .name("aaasssddd")
        .phoneNumber("01077778888")
        .build());

    // When
    FindUserResponse user = userService.getUserById("testId");

    // Then
    assertThat(user.getUser().getUserSeq()).isNotNull();
    assertThat(user.getUser().getLoginId()).isEqualTo("testId");
    assertThat(user.getUser().getName()).isEqualTo("aaasssddd");
  }
}