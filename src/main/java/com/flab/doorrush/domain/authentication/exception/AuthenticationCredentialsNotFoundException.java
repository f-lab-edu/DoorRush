package com.flab.doorrush.domain.authentication.exception;

public class AuthenticationCredentialsNotFoundException extends
    RuntimeException {

  public AuthenticationCredentialsNotFoundException(String message) {
    super(message);
  }
}
