package com.github.yumyum.mypage.dto;

import com.github.yumyum.member.dto.MemberResDto;
import com.github.yumyum.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMatchDTO {
    private int matchSn;
    private char matchCode;
    private LocalDate sendDt;
    private LocalDate matchDt;
    private int sendUser;
    private int receiveUser;

}
