package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;

  public void joinUser(UserDto userDto) {
    User user = userDto.toUser(userDto);
    joinUser(user);
  }

  public void joinUser(User user) {
    boolean isDuplicated = isDuplicatedId(user.getId());
    if (isDuplicated) {
      throw new DuplicatedUserIdException("이미 사용중인 아이디입니다.");
    }
    int insertResult = userMapper.insertUser(user);
    if (insertResult != 1) {
      throw new DuplicatedUserIdException("회원가입이 실패하였습니다.");
    }
  }

  public boolean isDuplicatedId(String id) {
    return userMapper.getCountById(id) == 1;
  }


  public UserDto getUserById(String userId) {
    Optional<User> user = userMapper.getUserById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException("회원정보가 없습니다.");
    }
    return user.get().toUserDto(user.get());
  }


  public HttpStatus login(LoginDto loginDto, HttpSession session) {
    HttpStatus result = HttpStatus.NOT_FOUND;
    String dbPassword = userMapper.getUserPasswordById(loginDto.getId());

    if (dbPassword.equals(loginDto.getPassword())) {
      session.setAttribute("login", "yes");
      result = HttpStatus.OK;
    }
    return result;
  }

  public HttpStatus logout(HttpSession session) {

    HttpStatus result = HttpStatus.NOT_FOUND;
    if (session.getAttribute("login").equals("yes")) {
      session.removeAttribute("login");
      result = HttpStatus.OK;
    }
    return result;
  }

}
