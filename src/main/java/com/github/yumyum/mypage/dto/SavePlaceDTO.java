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
public class SavePlaceDTO {
    private int saveSn;
    private int userSn;
    private int placeSn;
    private Date saveDt;

}
