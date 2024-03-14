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
public class CalenderDTO {
    private int calenderSn;
    private String calenderCn;
    private Date memoDt;
    private Date regDt;
    private int userSn;


}
