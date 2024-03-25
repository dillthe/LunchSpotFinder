package menu.yumyum.yumyum.common.exception;

import menu.yumyum.yumyum.common.exception.response.ErrorCode;
import menu.yumyum.yumyum.common.exception.response.ErrorMap;

import java.util.Collections;
import java.util.List;

public class EmailSendFailureException extends CustomException {

    private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_SEND_FAILURE;

    private EmailSendFailureException(List<ErrorMap> errorMapList) {
        super(EmailSendFailureException.class, ERROR_CODE, errorMapList);
    }

    public static EmailSendFailureException fire() {
        return new EmailSendFailureException(null);
    }

    public static EmailSendFailureException fire(ErrorMap errorMap) {
        return new EmailSendFailureException(Collections.singletonList(errorMap));
    }

    public static EmailSendFailureException fire(List<ErrorMap> errorMapList) {
        return new EmailSendFailureException(errorMapList);
    }

}
