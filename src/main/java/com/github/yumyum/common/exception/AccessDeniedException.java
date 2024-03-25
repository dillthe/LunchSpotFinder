package menu.yumyum.yumyum.common.exception;

import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class AccessDeniedException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.ACCESS_DENIED;

    private AccessDeniedException(List<ErrorMap> errorMapList) {
        super(AccessDeniedException.class, ERROR_CODE, errorMapList);
    }

    public static AccessDeniedException fire() {
        return new AccessDeniedException(null);
    }

    public static AccessDeniedException fire(ErrorMap errorMap) {
        return new AccessDeniedException(Collections.singletonList(errorMap));
    }

    public static AccessDeniedException fire(List<ErrorMap> errorMapList) {
        return new AccessDeniedException(errorMapList);
    }

}
