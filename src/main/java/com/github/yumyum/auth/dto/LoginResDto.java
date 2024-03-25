package menu.yumyum.yumyum.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import menu.yumyum.yumyum.common.security.JwtProvider;
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
