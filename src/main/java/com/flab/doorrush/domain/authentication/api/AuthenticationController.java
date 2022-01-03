package com.flab.doorrush.domain.authentication.api;

import com.flab.doorrush.domain.authentication.dto.request.AutoLoginRequest;
import com.flab.doorrush.domain.authentication.dto.request.LoginRequest;
import com.flab.doorrush.domain.authentication.service.AuthenticationService;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.service.UserService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;
  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<HttpStatus> login(@Valid @RequestBody AutoLoginRequest autoLoginRequest,
      @NotNull HttpSession session, @NotNull HttpServletResponse response) {
    LoginRequest loginRequest = new LoginRequest(autoLoginRequest.getId(),
        autoLoginRequest.getPassword());
    authenticationService.login(loginRequest, session);

    if (autoLoginRequest.isAutoLogin()) {
      FindUserResponse findUserResponse = userService.getUserById(autoLoginRequest.getId());
      User user = findUserResponse.getUser();
      String autoLoginCookieValue = URLEncoder.encode(user.getUserSeq() + "",
          StandardCharsets.UTF_8);
      Cookie autoLoginCookie = new Cookie("AUTOLOGIN", autoLoginCookieValue);
      autoLoginCookie.setHttpOnly(true);
      autoLoginCookie.setSecure(true);
      autoLoginCookie.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(autoLoginCookie);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity<HttpStatus> logout(@NotNull HttpSession session) {
    authenticationService.logout(session);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}


