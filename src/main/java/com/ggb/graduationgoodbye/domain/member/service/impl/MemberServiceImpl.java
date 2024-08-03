package com.ggb.graduationgoodbye.domain.member.service.impl;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.domain.member.dto.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.domain.member.vo.Member;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public Token join(MemberJoinRequest request) {
        // NOTE : 확장성 고려, OpenFeign 또는 추상화 도입 고민
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + request.accessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);

        log.info("Google Response >> {}", response.getBody());

        Member googleMember = new Gson().fromJson(response.getBody(), Member.class);

        Member member = Member.builder()
                .email(googleMember.getEmail())
                .profile(googleMember.getProfile())
                .nickname(request.nickname())
                .address(request.address())
                .phone(request.phone())
                .gender(request.gender())
                .build();
        memberRepository.save(member);
        
        return tokenService.getToken(makeAuthentication(member));
    }

    // NOTE : 추가 수정 필요 - 메서드 위치, 구현 방식
    private Authentication makeAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(
                member.getId().toString(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(memberRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberRepository.findById(id));
    }
}
