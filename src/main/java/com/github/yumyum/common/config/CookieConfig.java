package menu.yumyum.yumyum.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cookie")
public class CookieConfig {

    private String domain;
    private boolean secure;
    private String sameSite;

}
