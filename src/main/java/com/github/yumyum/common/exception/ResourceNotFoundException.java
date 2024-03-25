package menu.yumyum.yumyum.common.exception;


import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class ResourceNotFoundException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException(String errorMapList) {
        super(ResourceNotFoundException.class, ERROR_CODE, errorMapList);
    }

    public static ResourceNotFoundException fire() {
        return new ResourceNotFoundException(null);
    }

    public static ResourceNotFoundException fire(ErrorMap errorMap) {
        return new ResourceNotFoundException(Collections.singletonList(errorMap).toString());
    }

    public static ResourceNotFoundException fire(List<ErrorMap> errorMapList) {
        return new ResourceNotFoundException(errorMapList.toString());
    }


}
