package com.flab.doorrush.domain.authentication.api;

import static com.flab.doorrush.global.util.CookieUtils.getAutoLoginCookie;

import com.flab.doorrush.domain.authentication.dto.request.LoginRequest;
import com.flab.doorrush.domain.authentication.service.AuthenticationService;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.global.Response.BasicResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<BasicResponse> login(@Valid @RequestBody LoginRequest loginRequest,
      @NotNull HttpSession session, @NotNull HttpServletResponse response) {

    User user = authenticationService.login(loginRequest, session);
    if (loginRequest.isAutoLogin()) {
      Cookie autoLoginCookie = getAutoLoginCookie(String.valueOf(user.getUserSeq()));
      response.addCookie(autoLoginCookie);
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/logout")
  public ResponseEntity<HttpStatus> logout(@NotNull HttpSession session) {
    authenticationService.logout(session);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}


