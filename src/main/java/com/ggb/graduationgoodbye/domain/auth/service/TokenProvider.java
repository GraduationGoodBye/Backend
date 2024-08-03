package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.exception.ExpiredTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidJwtSignatureException;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {

    private final SecretKey SECRET_KEY;
    private final Long ACCESS_EXP;
    private final Long REFRESH_EXP;
    private final JwtParser jwtParser;
    private final AuthProvider authProvider;

    private static final String Bearer = "Bearer ";
    private static final String ROLE_CLAIM = "role";

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expired.access}") Long accessExp,
            @Value("${jwt.expired.refresh}") Long refreshExp,
            AuthProvider authProvider
    ) {
        this.authProvider = authProvider;
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
        this.ACCESS_EXP = accessExp;
        this.REFRESH_EXP = refreshExp;
        this.jwtParser = Jwts.parser().verifyWith(SECRET_KEY).build();
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, ACCESS_EXP);
    }

    public String createAccessToken(String refreshToken) {
        Claims claims = getClaimsFromToken(refreshToken);
        Authentication authentication = authProvider.getAuthentication(claims);
        return createAccessToken(authentication);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, REFRESH_EXP);
    }

    public String createRefreshToken(String accessToken) {
        Claims claims = getClaimsFromToken(accessToken);
        Authentication authentication = authProvider.getAuthentication(claims);
        return createRefreshToken(authentication);
    }

    private String createToken(Authentication authentication, Long exp) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + exp);

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(ROLE_CLAIM, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    // token validation
    public void validateToken(String token) {
        this.getClaimsFromToken(token);
    }

    // JWT 페이로드에 담긴 claims 조회
    public Claims getClaimsFromToken(String token) {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (SignatureException e) {
            throw new InvalidJwtSignatureException();
        } catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        } catch (Exception e) {
            throw new UnAuthenticatedException(e.getMessage());
        }
    }

    // Authorization 헤더에서 token 추출
    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(Bearer)) {
            return header.replace(Bearer, "");
        }
        return null;
    }
}
