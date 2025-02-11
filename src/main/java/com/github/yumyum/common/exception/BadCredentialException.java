package com.github.yumyum.common.exception;

import com.github.yumyum.common.exception.response.ErrorCode;
import com.github.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class BadCredentialException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.BAD_CREDENTIAL;

    private BadCredentialException(List<ErrorMap> errorMapList) {
        super(BadCredentialException.class, ERROR_CODE, errorMapList);
    }

    public static BadCredentialException fire() {
        return new BadCredentialException(null);
    }

    public static BadCredentialException fire(ErrorMap errorMap) {
        return new BadCredentialException(Collections.singletonList(errorMap));
    }

    public static BadCredentialException fire(List<ErrorMap> errorMapList) {
        return new BadCredentialException(errorMapList);
    }

}
