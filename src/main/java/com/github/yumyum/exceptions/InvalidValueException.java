package com.github.yumyum.exceptions;

//@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
