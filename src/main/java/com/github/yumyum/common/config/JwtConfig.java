package com.github.yumyum.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)([a-z]+)");

    private String secret;
    private String accessTokenValidationTime;
    private String refreshTokenValidationTime;

    private long accessTokenValidationMilliSeconds;
    private long refreshTokenValidationMilliSeconds;

    @PostConstruct
    private void init() {
        accessTokenValidationMilliSeconds = parseTimeToMilliseconds(accessTokenValidationTime);
        refreshTokenValidationMilliSeconds = parseTimeToMilliseconds(refreshTokenValidationTime);
    }

    private long parseTimeToMilliseconds(String timeString) {
        Matcher matcher = TIME_PATTERN.matcher(timeString.toLowerCase());

        if (matcher.matches()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            return switch (unit) {
                case "ms" -> amount;
                case "s" -> amount * 1000L;
                case "m" -> amount * 60 * 1000L;
                case "h" -> amount * 60 * 60 * 1000L;
                case "d" -> amount * 24 * 60 * 60 * 1000L;
                default -> throw new IllegalArgumentException("Invalid time unit: " + unit);
            };

        } else {
            throw new IllegalArgumentException("Invalid time format: " + timeString);
        }
    }

}
