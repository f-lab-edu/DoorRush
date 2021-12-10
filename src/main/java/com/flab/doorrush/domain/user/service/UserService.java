package com.flab.doorrush.domain.user.service;

import static com.flab.doorrush.domain.user.common.LoginEnum.Fail;
import static com.flab.doorrush.domain.user.common.LoginEnum.SUCCESS;

import com.flab.doorrush.domain.user.common.LoginEnum;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
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
    Optional<User> user = userMapper.getUserById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("회원정보가 없습니다.");
    }
    return user.get().toUserDto(user.get());
  }


  public LoginEnum login(LoginDto loginDto, HttpSession session) {

    String result = userMapper.checkUserPasswordById(loginDto.getId(), loginDto.getPassword());
    if (result.equals("success")) {
      session.setAttribute("login", "yes");
      return SUCCESS;
    }
    return Fail;
  }

  public LoginEnum logout(@NotNull HttpSession session) {

    if (("yes").equals(session.getAttribute("login"))) {
      session.invalidate();
      return SUCCESS;
    }
    return Fail;
  }
}
