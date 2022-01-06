package com.flab.doorrush.global.util;

import com.flab.doorrush.domain.user.domain.User;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;

public class CookieUtils {

  final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;

  public static Cookie getAutoLoginCookie(User user) {
    String cookieValue = String.valueOf(user.getUserSeq());
    String autoLoginCookieValue = URLEncoder.encode(cookieValue,
        StandardCharsets.UTF_8);
    Cookie autoLoginCookie = new Cookie("AUTOLOGIN", autoLoginCookieValue);
    autoLoginCookie.setHttpOnly(true);
    autoLoginCookie.setSecure(true);
    autoLoginCookie.setMaxAge(COOKIE_MAX_AGE);
    return autoLoginCookie;
  }
}
