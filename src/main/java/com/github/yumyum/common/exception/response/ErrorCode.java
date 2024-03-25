package menu.yumyum.yumyum.common.exception.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /* 권한 오류 */
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "E000", "자격증명에 실패하였습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E001", "잘못된 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "E002", "토큰이 만료되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "E003", "접근 권한이 없습니다."),
    NOT_AUTHENTICATED(HttpStatus.BAD_REQUEST, "E004", "인증되지 않는 상태입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E005", "해당 리소스를 찾을 수 없습니다."),
    DUPLICATED_RESOURCE(HttpStatus.CONFLICT, "E006", "중복된 리소스입니다."),
    FILE_SAVE_FAILURE(HttpStatus.BAD_REQUEST, "E007", "파일 저장에 실패하였습니다."),
    API_CALL_FAILURE(HttpStatus.BAD_REQUEST, "E008", "API 호출에 실패하였습니다."),
    EMAIL_SEND_FAILURE(HttpStatus.BAD_REQUEST, "E009", "이메일 발송에 실패하였습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "E999", "잘못된 파라미터입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    }
