package com.flab.doorrush.domain.user.exception.loginException;

/*Base class for authentication exceptions which are caused by a particular user account status (locked, disabled etc).*/
public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String message) {
    super(message);
  }

}
