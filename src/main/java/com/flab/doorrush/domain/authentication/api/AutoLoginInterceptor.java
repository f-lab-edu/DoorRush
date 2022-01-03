package com.flab.doorrush.domain.authentication.api;

import com.flab.doorrush.domain.authentication.service.AuthenticationService;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class AutoLoginInterceptor implements HandlerInterceptor {

  @Resource
  AuthenticationService authenticationService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    boolean result = true;
    Cookie[] cookies = request.getCookies();

    try {
      Cookie autoLoginCookie = cookies[1];
      String autoLoginCookieValue = autoLoginCookie.getValue();
      if (autoLoginCookieValue != null) {
        authenticationService.login(autoLoginCookieValue, request.getSession());
        response.setStatus(200);
        result = false;
      }
    } catch (Exception e) {
      result = true;
    } finally {
      return result;
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
