package com.flab.doorrush.domain.authentication.api;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationContainer;
import org.mockito.invocation.MockHandler;
import org.mockito.mock.MockCreationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
class AutoLoginInterceptorTest {

  @Autowired
  AutoLoginInterceptor autoLoginInterceptor;

  @Autowired
  AuthenticationController authenticationController;

  MockHandler<AuthenticationController> handler;

  @BeforeEach
  public void setUp() {
    handler = new MockHandler<AuthenticationController>() {
      @Override
      public Object handle(Invocation invocation) throws Throwable {
        return authenticationController;
      }

      @Override
      public MockCreationSettings<AuthenticationController> getMockSettings() {
        return null;
      }

      @Override
      public InvocationContainer getInvocationContainer() {
        return null;
      }
    };
  }

  @Test
  @DisplayName("preHandle 자동 로그인 테스트 결과 false 를 반환한다.")
  void preHandleAutoLoginTest() throws Exception {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Cookie JSESSIONCookie = new MockCookie("JSESSION", "123456789");
    Cookie AUTOLOGINCookie = new MockCookie("AUTOLOGIN", "25");
    request.setCookies(JSESSIONCookie, AUTOLOGINCookie);

    // When   Then
    assertFalse(autoLoginInterceptor.preHandle(request, response, handler));
    assertEquals(200, response.getStatus());
  }

  @Test
  @DisplayName("preHandle 일반 로그인 테스트 결과 true 를 반환한다.")
  void preHandleLoginTest() throws Exception {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Cookie JSESSIONCookie = new MockCookie("JSESSION", "123456789");
    request.setCookies(JSESSIONCookie);

    // When   Then
    assertTrue(autoLoginInterceptor.preHandle(request, response, handler));
    assertEquals(200, response.getStatus());
  }
}