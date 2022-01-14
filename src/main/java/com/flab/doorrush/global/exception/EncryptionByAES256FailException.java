package com.flab.doorrush.global.exception;

public class EncryptionByAES256FailException extends RuntimeException {

  public EncryptionByAES256FailException(String message, Exception e) {
    super(message, e);
  }
}
