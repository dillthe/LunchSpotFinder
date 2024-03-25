package com.github.yumyum.common.exception;

import com.github.yumyum.common.exception.response.ErrorCode;
import com.github.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class InvalidParameterException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.INVALID_PARAMETER;

    private InvalidParameterException(List<ErrorMap> errorMapList) {
        super(InvalidParameterException.class, ERROR_CODE, errorMapList);
    }

    public static InvalidParameterException fire() {
        return new InvalidParameterException(null);
    }

    public static InvalidParameterException fire(ErrorMap errorMap) {
        return new InvalidParameterException(Collections.singletonList(errorMap));
    }

    public static InvalidParameterException fire(List<ErrorMap> errorMapList) {
        return new InvalidParameterException(errorMapList);
    }

}
