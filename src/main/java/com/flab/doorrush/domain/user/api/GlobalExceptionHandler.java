package com.flab.doorrush.domain.user.api;

import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.user.exception.IdNotFoundException;
import com.flab.doorrush.domain.user.exception.InvalidPasswordException;
import com.flab.doorrush.domain.user.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.user.exception.SessionLoginIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice : 예외 처리, 바인딩 설정, 모델 객체 등을 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에
 *  사용하는 ControllerAdvice 를 생성하기 위한 어노테이션입니다. */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(IdNotFoundException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> loginExceptionHandler(InvalidPasswordException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> sessionLoginIdNotFoundException(
      SessionLoginIdNotFoundException e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<HttpStatus> sessionAuthenticationException(
      SessionAuthenticationException e) {
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(DuplicatedUserIdException.class)
  public ResponseEntity<String> duplicatedUserIdException(DuplicatedUserIdException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
        HttpStatus.BAD_REQUEST);
  }
}
