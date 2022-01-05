package com.flab.doorrush.domain.authentication.api;

import com.flab.doorrush.domain.authentication.service.AuthenticationService;
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

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("AUTOLOGIN")) {
        String autoLoginCookieValue = cookie.getValue();
        authenticationService.login(autoLoginCookieValue, request.getSession());
        response.setStatus(HttpStatus.OK.value());
        result = false;
      }
    }
    return result;
  }
}
