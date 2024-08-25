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
import com.ggb.graduationgoodbye.domain.member.controller.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.controller.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2MemberInfo;
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


  public Member findByEmail(String email) {
    return memberReader.findByEmail(email);
  }

  public Member findById(Long id) {
    return memberReader.findById(id);
  }

  public boolean existsByEmail(String email) {
    return memberReader.findByEmail(email) != null;
  }
}
