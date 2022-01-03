package com.flab.doorrush.global.exception;

import com.flab.doorrush.domain.authentication.exception.AutoLoginFailException;
import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import com.flab.doorrush.domain.authentication.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.authentication.exception.SessionLoginIdNotFoundException;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.authentication.exception.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice : 예외 처리, 바인딩 설정, 모델 객체 등을 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에
 *  사용하는 ControllerAdvice 를 생성하기 위한 어노테이션입니다. */
@ControllerAdvice
/* @Slf4j : 로깅에 대한 추상 레이어를 제공하는 interface의 모음인 Slf4j */
@Slf4j
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(IdNotFoundException e) {
    logger.error("등록된 아이디가 없습니다.", IdNotFoundException.class);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(InvalidPasswordException e) {
    logger.error("아이디 혹은 패스워드가 일치하지 않습니다.", InvalidPasswordException.class);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> sessionLoginIdNotFoundException(
      SessionLoginIdNotFoundException e) {
    logger.error("세션정보를 찾을 수 없습니다.", SessionLoginIdNotFoundException.class);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> sessionAuthenticationException(
      SessionAuthenticationException e) {
    logger.error("이미 해당 아이디로 로그인 중 입니다.", SessionAuthenticationException.class);
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);

  }

  @ExceptionHandler(DuplicatedUserIdException.class)
  public ResponseEntity<String> duplicatedUserIdException(DuplicatedUserIdException e) {
    logger.error("이미 사용중인 아이디입니다.", DuplicatedUserIdException.class);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AutoLoginFailException.class)
  public ResponseEntity<String> autoLoginFailException(AutoLoginFailException e) {
    logger.error("자동 로그인에 실패했습니다.", AutoLoginFailException.class);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
