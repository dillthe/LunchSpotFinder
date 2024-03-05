package com.github.yumyum.exceptions;


//@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptException extends RuntimeException {
    public NotAcceptException(String message) {
        super(message);
    }
}
