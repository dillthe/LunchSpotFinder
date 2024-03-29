package com.github.yumyum.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userSn;
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String userPhone;
}
