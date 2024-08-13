package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2MemberInfo;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.mapper.MemberMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberMapper memberMapper;
  private final TokenService tokenService;
  private final MemberInfoProvider memberInfoProvider;

  public TokenDto join(MemberJoinRequest request) {

    OAuth2MemberInfo memberInfo = memberInfoProvider.getInfo(request.snsType(),
        request.accessToken());

    log.info("OAuth2 Server Response >> {}", memberInfo);

    Member member = Member.builder()
        .snsType(SnsType.valueOf(request.snsType().toUpperCase()))
        .snsId(memberInfo.getSnsId())
        .email(memberInfo.getEmail())
        .profile(memberInfo.getProfile())
        .nickname(request.nickname())
        .address(request.address())
        .gender(request.gender())
        .age(request.age())
        .phone(request.phone())
        .build();

    memberMapper.save(member);

    Authentication authentication = tokenService.getAuthenticationByMember(member);

    return tokenService.getToken(authentication);
  }

  public Optional<Member> findByEmail(String email) {
    return memberMapper.findByEmail(email);
  }

  public Optional<Member> findById(Long id) {
    return memberMapper.findById(id);
  }

  public boolean existsByEmail(String email) {
    return memberMapper.findByEmail(email).isPresent();
  }
}
