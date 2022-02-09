package com.flab.doorrush.domain.authentication.service;

import static java.util.Objects.isNull;

import com.flab.doorrush.domain.authentication.dto.request.LoginRequest;
import com.flab.doorrush.domain.authentication.exception.AutoLoginFailException;
import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import com.flab.doorrush.domain.authentication.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.authentication.exception.SessionLoginIdNotFoundException;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.authentication.exception.IdNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  public static final String LOGIN_SESSION = "loginId";


  public User login(LoginRequest loginRequest, @NotNull HttpSession session) {
    if (loginRequest.getId().equals(session.getAttribute(LOGIN_SESSION))) {
      throw new SessionAuthenticationException("이미 해당 아이디로 로그인 중 입니다.");
    }
    User user = userMapper.selectUserById(loginRequest.getId())
        .orElseThrow(() -> new IdNotFoundException("등록된 아이디가 없습니다."));
    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      session.setAttribute(LOGIN_SESSION, loginRequest.getId());
    } else if (loginRequest.getPassword().equals(user.getPassword())) {
      session.setAttribute(LOGIN_SESSION, loginRequest.getId());
    } else {
      throw new InvalidPasswordException("아이디 혹은 패스워드가 일치하지 않습니다.");
    }
    return user;
  }

  public void login(@NotNull String autoLoginCookieValue, @NotNull HttpSession session) {
    User user = userMapper.selectUserBySEQ(Long.parseLong(autoLoginCookieValue))
        .orElseThrow(() -> new AutoLoginFailException("자동 로그인에 실패했습니다."));
    String encodedPassword = user.getPassword();
    LoginRequest loginRequest = new LoginRequest(user.getLoginId(), encodedPassword, true);
    login(loginRequest, session);
  }

  public void logout(@NotNull HttpSession session) {
    if (!isNull(session.getAttribute("loginId"))) {
      session.invalidate();
    } else {
      throw new SessionLoginIdNotFoundException("세션정보를 찾을 수 없습니다.");
    }
  }
}
