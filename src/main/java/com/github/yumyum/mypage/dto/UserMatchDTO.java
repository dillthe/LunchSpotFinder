package com.github.yumyum.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMatchDTO {
    private int matchSn;
    private char matchCode;
    private Date sendDt;
    private Date matchDt;
    private int sendUser;
    private int receiveUser;




}
