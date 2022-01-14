package com.flab.doorrush.global.exception;

public class DecryptionByAES256FailException extends RuntimeException {

  public DecryptionByAES256FailException(String message, Exception e) {
    super(message, e);
  }
}
