package com.flab.doorrush.domain.user.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.flab.doorrush.domain.user.api.UserController;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
public class LoginTest {

  @Autowired
  UserService userService;

  @Autowired
  UserController userController;

  protected MockHttpSession session;

  LoginDto correctLoginDto;

  LoginDto incorrectLoginDto;


  // Given
  @BeforeEach // @Test가 붙은 테스트 메서드가 실행되기 전에 먼저 @BeforeEach가 붙은 메서드가 실행되도록 하는 어노테이션
  public void setUp() {
    session = new MockHttpSession();
    session.setAttribute("login", "yes");

    correctLoginDto = new LoginDto("test1", "test1pw");
    incorrectLoginDto = new LoginDto("test1", "test222222222pw");
  }


  @AfterEach // @Test가 붙은 테스트 메서드가 실행된 후에 @AfterEach가 붙은 메서드가 실행되도록 하는 어노테이션
  public void clean() {
    session.clearAttributes();
  }


  @Test
  public void loginUserServiceTest() {

    // When
    HttpStatus correctInput = userService.login(correctLoginDto, session);
    HttpStatus incorrectInput = userService.login(incorrectLoginDto, session);

    // Then
    assertThat(HttpStatus.OK, is(correctInput));
    assertThat(HttpStatus.NOT_FOUND, is(incorrectInput));
  }

  @Test
  public void loginSessionTest() {

    // When
    userController.login(correctLoginDto, session);
    String loginAttributeValue = session.getAttribute("login").toString();

    // Then
    assertEquals("yes",loginAttributeValue);
    assertNotEquals("123",loginAttributeValue);
  }

  @Test
  public void logoutTest() {
    // Given
    userController.login(correctLoginDto, session);
    if (session != null) {
      assertThat("yes", is(session.getAttribute("login")));

      // When
      userController.logout(session);

      // Then
      assertThat(null, is(session.getAttribute("login")));
    }
  }
}
