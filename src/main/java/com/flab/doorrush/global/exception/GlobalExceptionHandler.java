package com.flab.doorrush.global.exception;

import com.flab.doorrush.domain.authentication.exception.AutoLoginFailException;
import com.flab.doorrush.domain.authentication.exception.InvalidPasswordException;
import com.flab.doorrush.domain.authentication.exception.SessionAuthenticationException;
import com.flab.doorrush.domain.authentication.exception.SessionLoginIdNotFoundException;
import com.flab.doorrush.global.Response.BasicResponse;
import com.flab.doorrush.domain.user.exception.DuplicatedUserIdException;
import com.flab.doorrush.domain.authentication.exception.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import com.flab.doorrush.domain.user.exception.NotExistsAddressException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice : 예외 처리, 바인딩 설정, 모델 객체 등을 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에
 *  사용하는 ControllerAdvice 를 생성하기 위한 어노테이션입니다. */
@ControllerAdvice
/* @Slf4j : 로깅에 대한 추상 레이어를 제공하는 interface의 모음인 Slf4을 사용하기 위한 어노테이션입니다.*/
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<BasicResponse> loginExceptionHandler(IdNotFoundException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler
  public ResponseEntity<BasicResponse> loginExceptionHandler(InvalidPasswordException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler
  public ResponseEntity<BasicResponse> sessionLoginIdNotFoundException(
      SessionLoginIdNotFoundException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler
  public ResponseEntity<BasicResponse<String>> sessionAuthenticationException(
      SessionAuthenticationException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BasicResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(DuplicatedUserIdException.class)
  public ResponseEntity<BasicResponse<String>> duplicatedUserIdException(
      DuplicatedUserIdException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(AutoLoginFailException.class)
  public ResponseEntity<String> autoLoginFailException(AutoLoginFailException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BasicResponse<String>> methodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BasicResponse.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
  }

  @ExceptionHandler(NotExistsAddressException.class)
  public ResponseEntity<BasicResponse<String>> methodArgumentNotValidException(NotExistsAddressException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(EncryptionByAES256FailException.class)
  public void encryptionByAES256FailException(EncryptionByAES256FailException e) {
    log.error(e.getMessage(), e, e.getCause());
  }

  @ExceptionHandler(DecryptionByAES256FailException.class)
  public void decryptionByAES256FailException(DecryptionByAES256FailException e) {
    log.error(e.getMessage(), e, e.getCause());
  }

}
