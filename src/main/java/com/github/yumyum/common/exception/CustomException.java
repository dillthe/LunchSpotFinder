package menu.yumyum.yumyum.common.exception;

import lombok.Getter;
import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.List;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    private final List<ErrorMap> errorMapList;

    public CustomException(Class<?> exceptionClass, ErrorCode errorCode, String errorMapList) {
        super(exceptionClass.getName() + " 발생!");
        this.errorCode = errorCode;
        this.errorMapList = null;
    }

    public CustomException(Class<?> exceptionClass, ErrorCode errorCode, List<ErrorMap> errorMapList) {
        super(exceptionClass.getName() + " 발생!");
        this.errorCode = errorCode;
        this.errorMapList = errorMapList;
    }

}
