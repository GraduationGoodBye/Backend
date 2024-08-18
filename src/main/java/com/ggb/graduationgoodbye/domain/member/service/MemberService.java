package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.artist.repository.ArtistRepository;
import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.exception.NotFoundMajorException;
import com.ggb.graduationgoodbye.domain.commonCode.exception.NotFoundUniversityException;
import com.ggb.graduationgoodbye.domain.commonCode.service.CommonCodeInfoProvider;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.controller.PromoteArtistDTO;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2MemberInfo;
import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ggb.graduationgoodbye.domain.s3.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final ArtistRepository artistRepository;
  private final TokenService tokenService;
  private final MemberInfoProvider memberInfoProvider;
  private final CommonCodeInfoProvider commonCodeInfoProvider;
  private final S3Util s3Util;

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

    memberRepository.save(member);

    Authentication authentication = tokenService.getAuthenticationByMember(member);

    return tokenService.getToken(authentication);
  }

  public Artist promoteArtist(Member member, PromoteArtistDTO.Request request, MultipartFile certificate){

    CommonCode university = commonCodeInfoProvider.findByUniversity(request.getUniversity())
            .orElseThrow(NotFoundUniversityException::new);
    CommonCode major = commonCodeInfoProvider.findByMajor(request.getMajor())
            .orElseThrow(NotFoundMajorException::new);
    String certificateUrl = s3Util.upload(certificate);
    String createId = String.valueOf(member.getId());

    Artist artist = Artist.builder()
            .member_id(member)
            .common_university_id(university)
            .common_major_id(major)
            .name(request.getName())
            .certificate_url(certificateUrl)
            .created_id(createId)
            .created_at(LocalDateTime.now())
            .updated_id(createId)
            .build();

    return artistRepository.save(artist);
  }

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Optional<Member> findById(Long id) {
    return memberRepository.findById(id);
  }

  public boolean existsByEmail(String email) {
    return memberRepository.findByEmail(email).isPresent();
  }
}
