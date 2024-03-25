package menu.yumyum.yumyum.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ReissueReqDto {

    @JsonIgnore
    private String refreshToken;
}
