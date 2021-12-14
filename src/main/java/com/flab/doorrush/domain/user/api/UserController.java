package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.dto.LoginDto;
import com.flab.doorrush.domain.user.dto.UserDto;
import com.flab.doorrush.domain.user.exception.loginException.IdNotFoundException;
import com.flab.doorrush.domain.user.exception.loginException.InvalidPasswordException;
import com.flab.doorrush.domain.user.exception.loginException.SessionLoginIdNotFoundException;
import com.flab.doorrush.domain.user.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
// @RequiredArgsConstructor : 초기화되지 않은 final 필드를 매개변수로 받는 생성자를 생성하는 어노테이션입니다.
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @PostMapping()
  public ResponseEntity<UserDto> joinUser(@RequestBody UserDto userDto) {
    userService.joinUser(userDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }


  @PostMapping("/login")
  public ResponseEntity<HttpStatus> login(@RequestBody LoginDto loginDto,
      HttpSession session) {

    userService.login(loginDto, session);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(IdNotFoundException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(InvalidPasswordException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/logout")
  public ResponseEntity<HttpStatus> logout(@NotNull HttpSession session) {
    userService.logout(session);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> sessionLoginIdNotFoundException(
      SessionLoginIdNotFoundException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
