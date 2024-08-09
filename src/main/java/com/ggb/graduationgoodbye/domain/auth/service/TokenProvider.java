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

  private final SecretKey ACCESS_SECRET;
  private final SecretKey REFRESH_SECRET;
  private final Long ACCESS_EXP;
  private final Long REFRESH_EXP;
  private final AuthProvider authProvider;

  private static final String Bearer = "Bearer ";
  private static final String ROLE_CLAIM = "role";

  public TokenProvider(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.access.expired}") Long accessExp,
      @Value("${jwt.refresh.secret}") String refreshSecret,
      @Value("${jwt.refresh.expired}") Long refreshExp,
      AuthProvider authProvider) {
    this.authProvider = authProvider;
    byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecret);
    byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecret);
    this.ACCESS_SECRET = Keys.hmacShaKeyFor(accessKeyBytes);
    this.REFRESH_SECRET = Keys.hmacShaKeyFor(refreshKeyBytes);
    this.ACCESS_EXP = accessExp;
    this.REFRESH_EXP = refreshExp;
  }

  // 토큰 생성 로직
  public String createAccessToken(Authentication authentication) {
    return createToken(authentication, ACCESS_SECRET, ACCESS_EXP);
  }

  public String createRefreshToken(Authentication authentication) {
    return createToken(authentication, REFRESH_SECRET, REFRESH_EXP);
  }

  private String createToken(Authentication authentication, SecretKey secret, Long exp) {
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
        .signWith(secret)
        .compact();
  }

  //
  public Authentication getAuthenticationByAccessToken(String accessToken) {
    Claims claims = getClaimsFromAccessToken(accessToken);
    return authProvider.getAuthentication(claims);
  }

  public Authentication getAuthenticationByRefreshToken(String refreshToken) {
    Claims claims = getClaimsFromRefreshToken(refreshToken);
    return authProvider.getAuthentication(claims);
  }

  // AuthorizationHeader에서 accessToken 추출
  public String getTokenFromAuthorizationHeader(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(header) && header.startsWith(Bearer)) {
      return header.replace(Bearer, "");
    }
    return null;
  }

  // 토큰 검증
  public void validateAccessToken(String accessToken) {
    validateToken(accessToken, ACCESS_SECRET);
  }

  public void validateRefreshToken(String refreshToken) {
    validateToken(refreshToken, REFRESH_SECRET);
  }

  private void validateToken(String token, SecretKey secretKey) {
    this.getClaimsFromToken(token, secretKey);
  }

  private Claims getClaimsFromAccessToken(String accessToken) {
    return getClaimsFromToken(accessToken, ACCESS_SECRET);
  }

  private Claims getClaimsFromRefreshToken(String refreshToken) {
    return getClaimsFromToken(refreshToken, REFRESH_SECRET);
  }

  // JWT 페이로드에 담긴 claims 조회
  private Claims getClaimsFromToken(String token, SecretKey secretKey) {
    try {
      return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
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
}
