package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.AuthProvider;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final TokenService tokenService;
  private final AuthProvider authProvider;

  public TokenDto join(MemberJoinRequest request) {
    // NOTE : 확장성 고려, OpenFeign 또는 추상화 도입 고민
    String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + request.accessToken());
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = new RestTemplate().exchange(userInfoEndpointUri,
        HttpMethod.GET, entity,
        String.class);

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

    Authentication authentication = authProvider.getAuthentication(member);

    return tokenService.getToken(authentication);
  }

  public Optional<Member> findByEmail(String email) {
    return Optional.ofNullable(memberRepository.findByEmail(email));
  }

  public boolean existsByEmail(String email) {
    return memberRepository.findByEmail(email) != null;
  }

  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(memberRepository.findById(id));
  }
}
