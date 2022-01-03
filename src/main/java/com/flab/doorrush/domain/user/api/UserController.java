package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.request.ChangePasswordRequest;
import com.flab.doorrush.domain.user.dto.request.JoinUserRequest;
import com.flab.doorrush.domain.user.dto.response.JoinUserResponse;
import com.flab.doorrush.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<JoinUserResponse> joinUser(
      @Valid @RequestBody JoinUserRequest joinUserRequest) {
    JoinUserResponse userResponse = userService.joinUser(joinUserRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
  }

  @PatchMapping("/{userSeq}/password")
  public ResponseEntity<Boolean> changePassword(@PathVariable Long userSeq,
      @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.ok(userService.changePassword(userSeq, changePasswordRequest));
  }

}
