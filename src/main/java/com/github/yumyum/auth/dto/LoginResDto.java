package com.github.yumyum.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yumyum.common.security.JwtProvider;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class LoginResDto {
    private String loginId;

    @JsonIgnore
    private JwtProvider.Token accessToken;
    @JsonIgnore
    private JwtProvider.Token refreshToken;

    @JsonProperty("accessToken")
    public String getAccessToken() {
        return accessToken.getValue();
    }

}
