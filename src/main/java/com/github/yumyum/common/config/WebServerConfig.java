package menu.yumyum.yumyum.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "web-server")
public class WebServerConfig {

    private String YumYum;

}
