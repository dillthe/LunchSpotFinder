package menu.yumyum.yumyum.common.exception;

import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class DuplicatedResourceException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DUPLICATED_RESOURCE;

    private DuplicatedResourceException(List<ErrorMap> errorMapList) {
        super(DuplicatedResourceException.class, ERROR_CODE, errorMapList);
    }

    public static DuplicatedResourceException fire() {
        return new DuplicatedResourceException(null);
    }

    public static DuplicatedResourceException fire(ErrorMap errorMap) {
        return new DuplicatedResourceException(Collections.singletonList(errorMap));
    }

    public static DuplicatedResourceException fire(List<ErrorMap> errorMapList) {
        return new DuplicatedResourceException(errorMapList);
    }

}
