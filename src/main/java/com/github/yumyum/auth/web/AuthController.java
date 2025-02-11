package com.github.yumyum.auth.web;

import lombok.RequiredArgsConstructor;
import com.github.yumyum.auth.dto.*;
import com.github.yumyum.auth.service.AuthService;
import com.github.yumyum.common.config.CookieConfig;
import com.github.yumyum.common.security.JwtProvider;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String COOKIE_HEADER_KEY = "Set-Cookie";

    private final CookieConfig cookieConfig;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto dto) {

        LoginResDto resDto = authService.login(dto);

        JwtProvider.Token refreshToken = resDto.getRefreshToken();
        ResponseCookie cookie = createCookieWithToken(refreshToken);

        return ResponseEntity.ok()
                .headers(httpHeaders -> httpHeaders.set("Set-Cookie", cookie.toString()))
                .body(resDto);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(SignUpReqDto dto) {
        authService.join(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth-num")
    public ResponseEntity<Void> checkAuthcode(CheckAuthCodeReqDto dto) {
        authService.checkAuthCode(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join-confirm")
    public ResponseEntity<Void> joinConfirm(SignUpConfirmReqDto dto) {
        authService.joinConfirm(dto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/login/status")
    public ResponseEntity<Void> getLoginStatus(LoginStatusReqDto dto) {
       authService.getLoginStatus(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reissue")
    public ResponseEntity<ReissueResDto> reissue(
            @RequestParam @ModelAttribute ReissueReqDto dto,
            @CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken
    ) {
        dto.setRefreshToken(refreshToken);

        ReissueResDto resDto = authService.reissue(dto);

        JwtProvider.Token newRefreshToken = resDto.getRefreshToken();

        /* 기존과 동일한 refresh 토큰일 경우 */
        if (newRefreshToken.getValue().equals(dto.getRefreshToken())) {
            return ResponseEntity.ok(resDto);

        } else { /* 신규 refresh 토큰일 경우 */
            ResponseCookie cookie = createCookieWithToken(newRefreshToken);

            return ResponseEntity.ok()
                    .headers(httpHeaders -> httpHeaders.set(COOKIE_HEADER_KEY, cookie.toString()))
                    .body(resDto);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestParam @ModelAttribute LogoutReqDto dto
    ) {
        ResponseCookie cookie = createExpiredCookie();

        authService.logout(dto);
        return ResponseEntity.ok()
                .headers(httpHeaders -> httpHeaders.set(COOKIE_HEADER_KEY, cookie.toString()))
                .build();
    }

    private ResponseCookie createCookieWithToken(JwtProvider.Token refreshToken) {
        return ResponseCookie.from("REFRESH_TOKEN", refreshToken.getValue())
                .maxAge(refreshToken.getExpiredTime() / 1000L) /* milliseconds > seconds */
                .path("/")
                .httpOnly(true)
                .domain(cookieConfig.getDomain())
                .secure(cookieConfig.isSecure())
                .sameSite(cookieConfig.getSameSite())
                .build();
    }

    private ResponseCookie createExpiredCookie() {
        return ResponseCookie.from("REFRESH_TOKEN", "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .domain(cookieConfig.getDomain())
                .secure(cookieConfig.isSecure())
                .sameSite(cookieConfig.getSameSite())
                .build();
    }
}
