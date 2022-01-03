package com.flab.doorrush.domain.user.service;

import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.dao.UserMapper;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.FindUserResponse;
import com.flab.doorrush.domain.user.dto.response.JoinUserResponse;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.UserNotFoundException;
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

  public boolean changePassword(Long userSeq, ChangePasswordRequest changePasswordRequest) {
    if (!isValidPassword(userSeq, changePasswordRequest.getOriginPassword())) {
      throw new InvalidPasswordException("패스워드가 일치하지 않습니다.");
    }
    User user = User.builder()
        .userSeq(userSeq)
        .password(passwordEncoder.encode(changePasswordRequest.getNewPassword()))
        .build();
    return userMapper.updatePassword(user) == 1;
  }

  private boolean isValidPassword(Long userSeq, String originPassword) {
    User user = userMapper.selectUserByUserSeq(userSeq)
        .orElseThrow(() -> new UserNotFoundException("회원정보가 없습니다."));
    return passwordEncoder.matches(originPassword, user.getPassword());
  }
}
