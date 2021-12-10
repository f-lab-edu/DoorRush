package com.flab.doorrush.domain.user.common;

import org.springframework.http.HttpStatus;

public enum LoginEnum {

  SUCCESS(HttpStatus.OK),
  FAIL(HttpStatus.NOT_FOUND);


  private final HttpStatus value;

  LoginEnum(HttpStatus value) {
    this.value = value;
  }

  public HttpStatus getValue() {
    return value;
  }
}
