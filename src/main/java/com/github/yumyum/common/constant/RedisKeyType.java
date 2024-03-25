package com.github.yumyum.common.constant;

import lombok.Getter;

/* Redis 저장 시, {TYPE}:{MEMBER_ID} 형식으로 키를 생성하기 위함. */
@Getter
public enum RedisKeyType {

    LOGIN, /* 로그인 */
    JOIN_AUTH, /* 회원가입 이메일 인증 */
    PASSWORD_AUTH, /* 비밀번호 찾기 이메일 인증 */
    INVITE /* 초대 회원 확인 */
}
