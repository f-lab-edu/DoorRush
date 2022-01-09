package com.flab.doorrush.domain.authentication.service;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.doorrush.domain.authentication.dto.request.LoginRequest;
import com.flab.doorrush.domain.authentication.exception.AutoLoginFailException;
import com.flab.doorrush.domain.authentication.exception.IdNotFoundException;
import com.flab.doorrush.domain.authentication.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class AuthenticationServiceTest {

  @Autowired
  AuthenticationService authenticationService;

  @Autowired
  PasswordEncoder passwordEncoder;


  @Test
  @DisplayName("로그인 성공 테스트 login메소드 실행 후 세션의 loginId 속성 값을 아이디 값과 비교한다.")
  public void loginSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginRequest loginRequest = new LoginRequest("test6", "test6pw", false);

    // When
    authenticationService.login(loginRequest, session);

    // Then
    MatcherAssert.assertThat(session.getAttribute("loginId"), is("test6"));
  }

  @Test
  @DisplayName("로그인 실패 테스트 없는 아이디일 경우 또는 일치하지않는 비밀번호 입력할 경우 예외를 발생시킨다.")
  public void loginFailDuplicatedLoginTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginRequest loginRequest = new LoginRequest("test6", "test6pw", false);
    authenticationService.login(loginRequest, session);

    // Then
    assertThrows(
        SessionAuthenticationException.class,
        // When
        () -> authenticationService.login(loginRequest, session));
  }

  @Test
  @DisplayName("자동 로그인 성공 테스트 AUTOLOGIN 쿠키 값으로 login 메서드 실행 후 확인")
  void autoLoginSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();

    // When
    authenticationService.login("25", session);

    // Then
    MatcherAssert.assertThat(session.getAttribute("loginId"), is("test6"));
  }

  @Test
  @DisplayName("자동 로그인 실패 테스트 AUTOLOGIN 쿠키 값으로 login 메서드 실행 후 확인")
  void autoLoginFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();

    // Then                                    // When
    assertThrows(AutoLoginFailException.class,
        () -> authenticationService.login("223112", session));
  }

  @Test
  @DisplayName("중복 로그인 실패 테스트 예외를 발생시킨다.")
  public void loginFailTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginRequest idNotFoundExceptionLoginRequest = new LoginRequest("test111111", "test22222222pw",
        false);
    // Then                                     // When
    assertThrows(IdNotFoundException.class,
        () -> authenticationService.login(idNotFoundExceptionLoginRequest, session));

    // Given
    LoginRequest InvalidPasswordException = new LoginRequest("test6", "test222222222pw", false);
    // Then
    assertThrows(
        InvalidPasswordException.class,
        // When
        () -> authenticationService.login(InvalidPasswordException, session));
  }

  @Test
  @DisplayName("로그아웃 성공 테스트 세션을 무효화한다.")
  public void logoutSuccessTest() {
    // Given
    MockHttpSession session = new MockHttpSession();
    LoginRequest loginRequest = new LoginRequest("test6", "test6pw", false);
    authenticationService.login(loginRequest, session);

    // When
    authenticationService.logout(session);

    // Then
    assertTrue(session.isInvalid());
  }
}