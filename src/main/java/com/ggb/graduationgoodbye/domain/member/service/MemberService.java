package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.artist.business.ArtistCreator;
import com.ggb.graduationgoodbye.domain.artist.business.ArtistValidator;
import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.commonCode.business.MajorReader;
import com.ggb.graduationgoodbye.domain.commonCode.business.UniversityReader;
import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.common.exception.NotFoundMajorException;
import com.ggb.graduationgoodbye.domain.member.business.MemberCreator;
import com.ggb.graduationgoodbye.domain.member.business.MemberProvider;
import com.ggb.graduationgoodbye.domain.member.business.MemberReader;
import com.ggb.graduationgoodbye.domain.member.business.MemberValidator;
import com.ggb.graduationgoodbye.domain.member.business.NicknameProvider;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.s3.utils.S3Util;
import java.util.List;
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

  private final NicknameProvider nicknameProvider;
  private final MemberProvider memberProvider;
  private final MemberCreator memberCreator;
  private final MemberReader memberReader;
  private final MemberValidator memberValidator;
  private final ArtistCreator artistCreator;
  private final ArtistValidator artistValidator;
  private final TokenService tokenService;
  private final UniversityReader universityReader;
  private final MajorReader majorReader;
  private final S3Util s3Util;

  /**
   * 회원 가입.
   */
  public TokenDto join(String snsType, OAuthUserInfoDto oAuthUserInfoDto,
      MemberJoinDto.Request request) {
    Member member = Member.builder()
        .snsType(SnsType.valueOf(snsType.toUpperCase()))
        .snsId(oAuthUserInfoDto.getSnsId())
        .email(oAuthUserInfoDto.getEmail())
        .profile(oAuthUserInfoDto.getProfile())
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
   * SNS 타입 유효성 검증
   */
  public void checkSnsType(String snsType) {
    memberValidator.validateSnsType(snsType);
  }

  /**
   * 회원 닉네임 중복 확인
   */
  public void checkNicknameExists(String nickname) {
    memberReader.checkNicknameExists(nickname);
  }

  /**
   * 회원 SNS 정보 조회
   */
  public Member findBySns(SnsDto dto) {
    return memberReader.findBySns(dto);
  }

  /**
   * 회원 정보 조회
   */
  public Member getById(Long id) {
    return memberReader.findById(id);
  }

  /**
   * 랜덤 닉네임 제공
   */
  public List<String> serveRandomNicknames() {
    return nicknameProvider.provideRandomNicknames();
  }

  public Member checkMemberExists(SnsDto snsDto, String oauthToken) {
    return memberReader.getMemberOrAuthException(snsDto, oauthToken);
  }
}
