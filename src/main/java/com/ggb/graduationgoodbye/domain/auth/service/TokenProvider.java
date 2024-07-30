package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.exception.InvalidJwtSignature;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {
    @Value("${jwt.secret}")
    private String tokenSecret;

    @Value("${jwt.expired.access}")
    private String accessExpireTime;

    @Value("${jwt.expired.refresh}")
    private String refreshExpireTime;

    private static final String ROLE_CLAIM = "role";

    private final TokenRepository tokenRepository;

    // access-token 생성
    public String createAccessToken(Authentication authentication){
        Long expireTime = Long.parseLong(accessExpireTime);
        return createToken(authentication, expireTime);
    }

    // refresh-token 생성
    public void createRefreshToken(Authentication authentication, String accessToken){
        Long expireTime = Long.parseLong(refreshExpireTime); // parsing String -> Long
        String refreshToken = createToken(authentication, expireTime); // refresh-token 생성
        Token token = Token.of(accessToken, refreshToken); // VO
        tokenRepository.save(token); // DB 저장
    }

    private String createToken(Authentication authentication, Long expireTime){
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(ROLE_CLAIM, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Authentication 객체 생성
    public Authentication getAuthentication(String token){
        Claims claims = getClaimsFromToken(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal,token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims){
        return Collections.singletonList(new SimpleGrantedAuthority(claims.get(ROLE_CLAIM).toString()));
    }

    // accessToken 재발급
    public String reissueAccessToken(String accessToken){
        if(StringUtils.hasText(accessToken)){
            Token token = tokenRepository.findByAccessToken(accessToken);
            String refreshToken = token.getRefreshToken();

            if(validateToken(refreshToken)){
                String reissuedAccessToken = createAccessToken(getAuthentication(refreshToken));
                token.updateAccessToken(reissuedAccessToken);
                tokenRepository.update(token);
                return reissuedAccessToken;
            }
        }
        return null;
    }

    // token validation
    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)){
            return false;
        }

        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().after(new Date());
    }

    // JWT 페이로드에 담긴 claims 조회
    private Claims getClaimsFromToken(String token) {
        try{
            return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }catch (SignatureException e) {
            throw new InvalidJwtSignature();
        }catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }catch(Exception e){
            throw new UnAuthenticatedException(e.getMessage());
        }
    }

    // secret key 복호화
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Authorization 헤더에서 token 추출
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader)) {
            return authorizationHeader.substring(7);
        } else {
            return null;
        }
    }
}
