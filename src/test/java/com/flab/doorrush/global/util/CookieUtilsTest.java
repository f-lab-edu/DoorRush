package com.flab.doorrush.global.util;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CookieUtilsTest {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("getAutoLoginCookie 메소드 성공 테스트 결과값으로 AUTOLOGIN 쿠키를 리턴한다")
  public void getAutoLoginCookieSuccessTest() {
    // Given
    String testValue = "testvaluetestvalue";
    // When
    Cookie autoLoginCookie = CookieUtils.getAutoLoginCookie(testValue);
    // Then
    assertEquals("AUTOLOGIN", autoLoginCookie.getName());
    assertEquals(SecurityUtils.getEncryptedValue("testvaluetestvalue"), autoLoginCookie.getValue());
    assertEquals("testvaluetestvalue", SecurityUtils.getDecryptedValue(autoLoginCookie.getValue()));
  }
}