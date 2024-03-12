package com.github.yumyum.map.web.dto.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {
    private String resultCode;
    private String resultMsg;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}