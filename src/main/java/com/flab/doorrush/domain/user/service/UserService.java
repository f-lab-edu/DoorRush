package com.flab.doorrush.domain.user.service;

import static java.util.Objects.isNull;

import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.IdNotFoundException;
import com.flab.doorrush.domain.user.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
import com.flab.doorrush.domain.user.exception.SessionLoginIdNotFoundException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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


  public UserDto getUserById(String id) {
    Optional<User> user = userMapper.selectUserById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("회원정보가 없습니다.");
    }
    return user.get().toUserDto(user.get());
  }


  public void login(LoginDto loginDto, HttpSession session) {
    User user = userMapper.selectUserById(loginDto.getId())
        .orElseThrow(() -> new IdNotFoundException("등록된 아이디가 없습니다."));

    if (loginDto.getPassword().equals(user.getPassword())) {
      session.setAttribute("loginId", loginDto.getId());
    } else {
      throw new InvalidPasswordException("아이디 혹은 패스워드가 일치하지 않습니다.");
    }
  }


  public void logout(@NotNull HttpSession session) {
    if (!isNull(session.getAttribute("loginId"))) {
      session.invalidate();
    } else {
      throw new SessionLoginIdNotFoundException("세션정보를 찾을 수 없습니다.");
    }
  }

}
