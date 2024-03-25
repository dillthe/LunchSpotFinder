package menu.yumyum.yumyum.auth.dto;

import lombok.Data;

@Data
public class CheckAuthCodeReqDto {

    private String authCode;
    private String loginId;
    private String password;
    private String memberName;
    private String phoneNum;

}
