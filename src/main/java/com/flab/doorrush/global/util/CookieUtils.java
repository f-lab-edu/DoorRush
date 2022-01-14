package com.flab.doorrush.global.util;

import javax.servlet.http.Cookie;

public class CookieUtils {

  final static int AUTOLOGIN_COOKIE_MAX_AGE = 60 * 60 * 24 * 30;

  public static Cookie getAutoLoginCookie(String cookieValue) {
    String encryptedCookieValue = SecurityUtils.getEncryptedValue(cookieValue);
    Cookie autoLoginCookie = new Cookie("AUTOLOGIN", encryptedCookieValue);
    autoLoginCookie.setHttpOnly(true);
    autoLoginCookie.setSecure(true);
    autoLoginCookie.setMaxAge(AUTOLOGIN_COOKIE_MAX_AGE);
    return autoLoginCookie;
  }
}
