package com.flab.doorrush.domain.authentication.api;


import static com.flab.doorrush.global.util.CookieUtils.AUTOLOGIN_COOKIE_NAME;

import com.flab.doorrush.domain.authentication.service.AuthenticationService;
import com.flab.doorrush.global.util.SecurityUtils;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;


public class AutoLoginInterceptor implements HandlerInterceptor {

  @Resource
  AuthenticationService authenticationService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    boolean result = true;
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return result;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(AUTOLOGIN_COOKIE_NAME)) {
        String autoLoginCookieValue = cookie.getValue();
        String decryptedAutoLoginCookieValue = SecurityUtils.getDecryptedValue(
            autoLoginCookieValue);
        authenticationService.login(decryptedAutoLoginCookieValue, request.getSession());
        response.setStatus(HttpStatus.OK.value());
        result = false;
      }
    }
    return result;
  }
}
