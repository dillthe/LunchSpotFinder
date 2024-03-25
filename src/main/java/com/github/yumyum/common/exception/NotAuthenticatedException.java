package menu.yumyum.yumyum.common.exception;

import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class NotAuthenticatedException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.NOT_AUTHENTICATED;

    private NotAuthenticatedException(List<ErrorMap> errorMapList) {
        super(NotAuthenticatedException.class, ERROR_CODE, errorMapList);
    }

    public static NotAuthenticatedException fire() {
        return new NotAuthenticatedException(null);
    }

    public static NotAuthenticatedException fire(ErrorMap errorMap) {
        return new NotAuthenticatedException(Collections.singletonList(errorMap));
    }

    public static NotAuthenticatedException fire(List<ErrorMap> errorMapList) {
        return new NotAuthenticatedException(errorMapList);
    }

}
