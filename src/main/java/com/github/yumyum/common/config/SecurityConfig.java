package menu.yumyum.yumyum.common.config;

import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.common.security.JwtAuthenticationFilter;
import menu.yumyum.yumyum.common.security.JwtProvider;
import menu.yumyum.yumyum.common.util.ObjectUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PERMIT_URL_ARRAY = {

            /* swagger v3 */
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",

            /* AuthController */
            "/auth/**",

            /* redis Controller */
            "/redis/**",

            /* 로그인 Page */
            "/login/**",

            /* 회원가입 Page */
            "/join/**",

            /* 이메일 인증 Page */
            "/email-authentication/**",
            "/**",
    };

    private final JwtProvider jwtProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조 */
                .httpBasic(HttpBasicConfigurer::disable)
                /* JWT 기반이므로 사용하지 않음 */
                .csrf(AbstractHttpConfigurer::disable)
                /* cross origin 허용 */
                .cors(corsConfigurer -> {
                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.addAllowedOriginPattern("*");
                    configuration.addAllowedHeader("*");
                    configuration.addAllowedMethod("*");
                    configuration.setAllowCredentials(true);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);

                    corsConfigurer.configurationSource(source);
                })
                /* custom login page 사용 */
                .formLogin(FormLoginConfigurer::disable)
                /* custom logout 기능 사용 */
                .logout(LogoutConfigurer::disable)
                /* 세션 사용하지 않음 */
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                /* Role 설정이 필요한 URL이 없음 */
                .authorizeHttpRequests(requests ->
                                requests.requestMatchers(PERMIT_URL_ARRAY).permitAll()
//                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                                        .anyRequest().authenticated()
//                                        .anyRequest().permitAll()
                )
                /* 사용자 검증 전 JWT 인증 필터 적용 */
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                        /* 권한 오류 발생 시 */
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter().write(ObjectUtil.asString(
                                Map.of(
                                        "message", "권한이 없는 사용자입니다."
                                )));
                    });
                    exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                        /* 인증 문제 발생 시 */
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter().write(ObjectUtil.asString(
                                Map.of(
                                        "message", "인증되지 않은 사용자입니다."
                                )
                        ));
                    });
                });

        return http.build();
    }
}
