package com.flab.doorrush.domain.user.exception.loginException;

//Base class for authentication exceptions which are caused by a particular user account status (locked, disabled etc).
public class AccountStatusException extends RuntimeException {

  public AccountStatusException(String message) {
    super(message);
  }

}
