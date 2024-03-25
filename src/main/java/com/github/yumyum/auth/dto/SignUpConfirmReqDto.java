package menu.yumyum.yumyum.auth.dto;

import lombok.Data;

@Data
public class SignUpConfirmReqDto {

    private String authCode;
    private String loginId;

}
