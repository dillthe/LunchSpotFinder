package com.github.yumyum.common.exception.response;

public interface ErrorMessage {

    String INVALID_ACCOUNT = "잘못된 계정입니다.";

    String INVALID_REFRESH_TOKEN = "잘못된 리프레쉬 토큰입니다.";

    String INVALID_AUTH_NUM = "잘못된 인증번호입니다.";

    String NOT_FOUND_MEMBER = "존재하지 않는 사용자입니다.";

    String MEMBER_NOT_FOUND = "존재하지 않는 맴버입니다.";

}
