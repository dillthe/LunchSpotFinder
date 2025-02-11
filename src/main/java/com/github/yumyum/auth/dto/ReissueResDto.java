package com.github.yumyum.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import com.github.yumyum.common.security.JwtProvider;

@Data
@Builder
public class ReissueResDto {

    @JsonIgnore
    private JwtProvider.Token accessToken;
    @JsonIgnore
    private JwtProvider.Token refreshToken;

    public String getAccessToken() {
        return accessToken.getValue();
    }

}
