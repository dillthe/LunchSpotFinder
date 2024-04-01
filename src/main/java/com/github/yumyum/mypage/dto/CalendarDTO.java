package com.github.yumyum.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {
    private Integer calendarSn;
    private String calendarCn;
    private LocalDate memoDt;
    private LocalDate regDt;
    private Integer memberId;


}
