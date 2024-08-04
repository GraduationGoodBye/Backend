package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class AuthProvider {

    private static final String ROLE_CLAIM = "role";

    public Authentication getAuthentication(Claims claims){
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public Authentication getAuthentication(Member member){
        List<SimpleGrantedAuthority> authorities = getAuthorities(member);
        User principal = new User(member.getId().toString(), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Member member) {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(claims.get(ROLE_CLAIM).toString()));
    }
}
