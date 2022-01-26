package com.flab.doorrush.domain.authentication.aspect;

import com.flab.doorrush.domain.authentication.exception.AuthenticationCredentialsNotFoundException;
import com.flab.doorrush.domain.authentication.service.AuthenticationService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class CheckLoginAspect {

  private final HttpSession httpSession;

  @Before("@annotation(com.flab.doorrush.domain.authentication.annotation.CheckLogin)")
  public void checkLogin() throws AuthenticationCredentialsNotFoundException {
    String currentId = (String) httpSession.getAttribute(AuthenticationService.LOGIN_SESSION);
    if (currentId == null || currentId.equals("null")) {
      throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
    }
  }
}
