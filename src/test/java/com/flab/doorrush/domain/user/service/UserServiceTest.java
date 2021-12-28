package com.flab.doorrush.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.exception.AutoLoginFailException;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.IdNotFoundException;
import com.flab.doorrush.domain.user.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/*
 * @SpringBootTest : SpringBoot 기능을 제공해주며 SpringBoot 통합테스트를 할 때 사용되는 주석
 * @SpringBootTest 어노테이션은 Spring Main Application(@SpringBootApplication)을 찾아가 하위의 모든 Bean을 Scan
 * */
@SpringBootTest
@Transactional
class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

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

  @Test
  @DisplayName("로그인 성공 테스트 login메소드 실행 후 세션의 loginId 속성 값을 아이디 값과 비교한다.")
  public void loginSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test6", "test6pw");

    // When
    userService.login(loginDto, session);

    // Then
    MatcherAssert.assertThat(session.getAttribute("loginId"), is("test6"));
  }

  @Test
  @DisplayName("로그인 실패 테스트 없는 아이디일 경우 또는 일치하지않는 비밀번호 입력할 경우 예외를 발생시킨다.")
  public void loginFailDuplicatedLoginTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test6", "test6pw");
    userService.login(loginDto, session);

    // Then
    assertThrows(
        com.flab.doorrush.domain.user.exception.SessionAuthenticationException.class,
        // When
        () -> userService.login(loginDto, session));
  }

  @Test
  @DisplayName("자동 로그인 성공 테스트 AUTOLOGIN 쿠키 값으로 login 메서드 실행 후 확인")
  void autoLoginSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();

    // When
    userService.login("25", session);

    // Then
    MatcherAssert.assertThat(session.getAttribute("loginId"), is("test6"));
  }

  @Test
  @DisplayName("자동 로그인 실패 테스트 AUTOLOGIN 쿠키 값으로 login 메서드 실행 후 확인")
  void autoLoginFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();

    // Then                                    // When
    assertThrows(AutoLoginFailException.class, () -> userService.login("223112", session));
  }

  @Test
  @DisplayName("중복 로그인 실패 테스트 예외를 발생시킨다.")
  public void loginFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto IdNotFoundExceptionLoginDto = new LoginDto("test111111", "test22222222pw");
    // Then                                     // When
    assertThrows(IdNotFoundException.class,
        () -> userService.login(IdNotFoundExceptionLoginDto, session));

    // Given
    LoginDto InvalidPasswordException = new LoginDto("test6", "test222222222pw");
    // Then
    assertThrows(
        com.flab.doorrush.domain.user.exception.InvalidPasswordException.class,
        // When
        () -> userService.login(InvalidPasswordException, session));
  }

  @Test
  @DisplayName("로그아웃 성공 테스트 세션을 무효화한다.")
  public void logoutSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginDto loginDto = new LoginDto("test6", "test6pw");
    userService.login(loginDto, session);

    // When
    userService.logout(session);

    // Then
    assertTrue(session.isInvalid());
  }

  @Test
  @DisplayName("비밀번호 암호화 테스트")
  public void passwordEncryptTest() {
    // Given
    String password = "12345";
    // When
    String encodePassword = passwordEncoder.encode(password);
    // Then
    assertNotEquals(password, encodePassword);
    assertTrue(passwordEncoder.matches(password, encodePassword));
  }

  @Test
  @DisplayName("아이디로 회원 정보 조회 후 암호화된 비밀번호 매칭 테스트")
  public void passwordEncryptWhtigetUserByIdTest() {
    // Given
    String reqLoginId = "test6";
    // When
    FindUserResponse user = userService.getUserById(reqLoginId);
    // Then
    assertTrue(passwordEncoder.matches("test6pw", user.getUser().getPassword()));
  }

  @Test
  @DisplayName("비밀번호 변경 성공 테스트")
  public void changePasswordSuccessTest() {
    // Given
    Long userSeq = 25L;
    String originPassword = "test6pw";
    String newPassword = "test6pwChange";
    // When
    boolean isUpdate = userService.changePassword(userSeq, ChangePasswordRequest.builder()
        .originPassword(originPassword).newPassword(newPassword)
        .build());
    // Then
    assertTrue(isUpdate);
  }

  @Test
  @DisplayName("비밀번호 변경 실패 테스트 (기존 비밀번호 불일치)")
  public void changePasswordFailTest() {
    // Given
    Long userSeq = 25L;
    String originPassword = "test6pwfail";
    String newPassword = "test6pwChange";
    // Then
    assertThrows(InvalidPasswordException.class,
        // When
        () -> userService.changePassword(userSeq, ChangePasswordRequest.builder()
            .originPassword(originPassword).newPassword(newPassword)
            .build()));

  }
}
