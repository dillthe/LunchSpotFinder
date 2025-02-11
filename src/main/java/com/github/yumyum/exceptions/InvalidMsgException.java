package com.github.yumyum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidMsgException extends RuntimeException {
    public InvalidMsgException(String message) {
        super(message);
    }
}
