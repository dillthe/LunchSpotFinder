package menu.yumyum.yumyum.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginStatusResDto {

    private boolean loggedIn;

}
