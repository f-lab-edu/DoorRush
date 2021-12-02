package com.flab.doorrush.domain.user.exception;

public class DuplicateUserIdException extends RuntimeException {

    public DuplicateUserIdException(String message) {
        super(message);
    }

    public DuplicateUserIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
