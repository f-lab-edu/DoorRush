package com.flab.doorrush.domain.user.service;

import static java.util.Objects.isNull;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.dto.response.JoinUserResponse;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.exception.AutoLoginFailException;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.IdNotFoundException;
import com.flab.doorrush.domain.user.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import com.flab.doorrush.domain.user.exception.SessionLoginIdNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public JoinUserResponse joinUser(JoinUserRequest joinUserRequest) {
    userMapper.selectUserById(joinUserRequest.getLoginId()).ifPresent(user -> {
      throw new DuplicatedUserIdException("이미 사용중인 아이디입니다.");
    });
    joinUserRequest.setPassword(passwordEncoder.encode(joinUserRequest.getPassword()));
    User user = joinUserRequest.toEntity();
    userMapper.insertUser(user);
    return JoinUserResponse.from(user);
  }

  public FindUserResponse getUserById(String userId) {
    User user = userMapper.selectUserById(userId)
        .orElseThrow(() -> new UserNotFoundException("회원정보가 없습니다."));
    return FindUserResponse.from(user);
  }

  public void login(LoginDto loginDto, HttpSession session) {
    if (loginDto.getId().equals(session.getAttribute("loginId"))) {
      throw new SessionAuthenticationException("이미 해당 아이디로 로그인 중 입니다.");
    }
    User user = userMapper.selectUserById(loginDto.getId())
        .orElseThrow(() -> new IdNotFoundException("등록된 아이디가 없습니다."));
    if (loginDto.getPassword().equals(user.getPassword())) {
      session.setAttribute("loginId", loginDto.getId());
    } else {
      throw new InvalidPasswordException("아이디 혹은 패스워드가 일치하지 않습니다.");
    }
  }

  public void login(String autoLoginCookieValue, HttpSession session) {
    User user = userMapper.selectUserBySEQ(Long.parseLong(autoLoginCookieValue))
        .orElseThrow(() -> new AutoLoginFailException("자동 로그인에 실패했습니다."));
    LoginDto loginDto = new LoginDto(user.getLoginId(), user.getPassword());
    login(loginDto, session);
  }

  public void setCookie(LoginDto loginDto, HttpServletResponse response) {
    User user = userMapper.selectUserById(loginDto.getId())
        .orElseThrow(() -> new IdNotFoundException("등록된 아이디가 없습니다."));
    String autoLoginCookieValue = URLEncoder.encode(user.getUserSeq() + "",
        StandardCharsets.UTF_8);
    Cookie autoLoginCookie = new Cookie("AUTOLOGIN", autoLoginCookieValue);
    autoLoginCookie.setHttpOnly(true);
    autoLoginCookie.setSecure(true);
    autoLoginCookie.setMaxAge(60 * 60 * 24 * 30);
    response.addCookie(autoLoginCookie);
  }

  public void logout(@NotNull HttpSession session) {
    if (!isNull(session.getAttribute("loginId"))) {
      session.invalidate();
    } else {
      throw new SessionLoginIdNotFoundException("세션정보를 찾을 수 없습니다.");
    }
  }
}
