package com.flab.doorrush.global.util;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.doorrush.domain.user.domain.User;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CookieUtilsTest {

  @Test
  @DisplayName("getAutoLoginCookie 메소드 성공 테스트 결과값으로 AUTOLOGIN 쿠키를 리턴한다")
  public void getAutoLoginCookieSuccessTest() {
    // Given
    User user = new User((long) 123456789, "test01Id", "test01Pw", "test01Name", "01000000000",
        "email");
    // When
    Cookie autoLoginCookie = CookieUtils.getAutoLoginCookie(user);
    // Then
    assertEquals(autoLoginCookie.getName(), "AUTOLOGIN");
    assertEquals(autoLoginCookie.getValue(), "123456789");
  }
}