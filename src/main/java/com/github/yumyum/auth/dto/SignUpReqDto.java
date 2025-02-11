package com.github.yumyum.auth.dto;

import lombok.Data;

@Data
public class SignUpReqDto {

    private String loginId; // 아이디 + 이메일
    private String password;
    private String memberName;
    private String phoneNum;
}