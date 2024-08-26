package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import com.ggb.graduationgoodbye.domain.artist.common.exception.DuplicationArtistException;
import com.ggb.graduationgoodbye.domain.artist.business.ArtistCreator;
import com.ggb.graduationgoodbye.domain.artist.business.ArtistValidator;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.common.exception.NotFoundMajorException;
import com.ggb.graduationgoodbye.domain.commonCode.business.MajorReader;
import com.ggb.graduationgoodbye.domain.commonCode.business.UniversityReader;
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.controller.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2InfoDto;
import com.ggb.graduationgoodbye.domain.member.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import com.ggb.graduationgoodbye.domain.s3.utils.S3Util;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 멤버 Service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberCreator memberCreator;
  private final MemberReader memberReader;
  private final ArtistCreator artistCreator;
  private final ArtistValidator artistValidator;
  private final TokenService tokenService;
  private final MemberInfoProvider memberInfoProvider;
  private final UniversityReader universityReader;
  private final MajorReader majorReader;
  private final S3Util s3Util;
  private final MemberProvider memberProvider;

  /**
   * 회원 가입.
   */
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

    memberCreator.save(member);

    Authentication authentication = tokenService.getAuthenticationByMember(member);

    return tokenService.getToken(authentication);
  }

  /**
   * 작가 회원 전환 요청.
   */
  public Artist promoteArtist(PromoteArtistDto.Request request,
      MultipartFile certificate) {

    Long memberId = memberProvider.getCurrentMemberId();
    Member member = memberReader.findById(memberId);

    /* 작가 회원 요청 중복 검사 */
    artistValidator.checkArtistDuplication(memberId);

    CommonCode university = universityReader.findUniversity(request.getUniversity());
    CommonCode major = majorReader.findByMajor(request.getMajor())
        .orElseThrow(NotFoundMajorException::new);
    String certificateUrl = s3Util.upload(certificate);
    String createId = String.valueOf(member.getId());

    Artist artist = Artist.builder()
        .memberId(member)
        .universityId(university)
        .majorId(major)
        .name(request.getName())
        .certificateUrl(certificateUrl)
        .createdId(createId)
        .build();

    return artistCreator.save(artist);
  }

  /**
   * 회원 SNS 정보 조회
   */
  public Member findBySns(SnsDto dto, String accessToken) {
    return memberReader.findBySns(dto, accessToken);
  }

  /**
   * 회원 정보 조회
   */
  public Member findById(Long id) {
    return memberReader.findById(id);
  }
}
