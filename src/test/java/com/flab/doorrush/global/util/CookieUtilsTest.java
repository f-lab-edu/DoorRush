package com.flab.doorrush.global.util;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CookieUtilsTest {


  @Test
  @DisplayName("getAutoLoginCookie 메소드 성공 테스트 결과값으로 AUTOLOGIN 쿠키를 리턴한다")
  public void getAutoLoginCookieTest() {
    // Given
    String testValue = "testvaluetestvalue";
    // When
    Cookie autoLoginCookie = CookieUtils.getAutoLoginCookie(testValue);
    // Then
    assertEquals(CookieUtils.AUTOLOGIN_COOKIE_NAME, autoLoginCookie.getName());
    String decryptedCookieValue = SecurityUtils.getDecryptedValue(autoLoginCookie.getValue());
    assertEquals("testvaluetestvalue", decryptedCookieValue);
    assertNotEquals("testvalue", decryptedCookieValue);
    assertTrue(autoLoginCookie.getSecure());
    assertEquals(autoLoginCookie.getMaxAge(), 60 * 60 * 24 * 30);
  }
}