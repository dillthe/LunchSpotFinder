package com.github.yumyum.map.web.dto.restaurant;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse<RestaurantDTO> {
     private Header header;
    private String resultCode;
    private String resultMsg;
    private List<RestaurantDTO> body;

     public Header getHeader() {
         return header;
     }

     public void setHeader(Header header) {
         this.header = header;
     }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<RestaurantDTO> getBody() {
        return body;
    }

    public void setBody(List<RestaurantDTO> body) {
        this.body = body;
    }
}
