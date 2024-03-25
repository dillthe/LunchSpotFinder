package com.github.yumyum.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.github.yumyum.common.config.JwtConfig;
import com.github.yumyum.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Getter
    @AllArgsConstructor
    public static class Token {
        private String value;
        private long expiredTime;
    }

    @Getter
    public enum TokenType {
        ACCESS, REFRESH
    }

    private static final String JWT_HEADER_KEY = "Authorization";
    private static final String JWT_SCHEME = "Bearer ";

    private final JwtConfig jwtConfig;

    private final CustomUserDetailsService customUserDetailsService;

    private Key secretKey;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public Token toToken(String tokenString, TokenType type) {
        long expTime;
        if (type == TokenType.ACCESS) {
            expTime = jwtConfig.getAccessTokenValidationMilliSeconds();
        } else if (type == TokenType.REFRESH) {
            expTime = jwtConfig.getRefreshTokenValidationMilliSeconds();
        } else {
            throw new IllegalArgumentException("잘못된 토큰 타입입니다.");
        }

        return new Token(tokenString, expTime);
    }

    public Token createAccessToken(Member member) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getId()));
        return createToken(claims, jwtConfig.getAccessTokenValidationMilliSeconds());
    }

    public Token createRefreshToken(Member member) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getId()));
        return createToken(claims, jwtConfig.getRefreshTokenValidationMilliSeconds());
    }

    public String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader(JWT_HEADER_KEY);

        if (authorization == null) {
            return null;
        }

        if (!authorization.substring(0, JWT_SCHEME.length()).equalsIgnoreCase(JWT_SCHEME)) {
            return null;
        }

        return authorization.split(" ")[1].trim();
    }

    public boolean isExpiration(String token, Date date) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            /* 만료 됐을 경우 */
            return claims.getBody().getExpiration().before(date);

        } catch (Exception e) {
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String memberId = getMemberId(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Token createToken(Claims claims, Long expTime) {
        Date now = new Date();
        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new Token(tokenString, expTime);
    }

    // 토큰에 담겨 있는 Member ID 획득
    private String getMemberId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

}
