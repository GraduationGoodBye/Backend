package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2MemberInfo;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final TokenService tokenService;
  private final MemberInfoProvider memberInfoProvider;

  public TokenDto join(MemberJoinDto.Request request) {

    OAuth2InfoDto memberInfo = memberInfoProvider.getInfo(request.getSnsType(),
        request.getAccessToken());

    log.info("OAuth2 Server Response >> {}", memberInfo);

    Member member = Member.builder()
        .snsType(SnsType.valueOf(request.getSnsType().toUpperCase()))
        .snsId(memberInfo.getSnsId())
        .email(memberInfo.getEmail())
        .profile(memberInfo.getProfile())
        .nickname(request.getNickname())
        .address(request.getAddress())
        .gender(request.getGender())
        .age(request.getAge())
        .phone(request.getPhone())
        .build();

    memberRepository.save(member);

    Authentication authentication = tokenService.getAuthenticationByMember(member);

    return tokenService.getToken(authentication);
  }

  public Optional<Member> findBySns(SnsDto dto) {
    return memberRepository.findBySns(dto);
  }

  public Optional<Member> findById(Long id) {
    return memberRepository.findById(id);
  }
}
