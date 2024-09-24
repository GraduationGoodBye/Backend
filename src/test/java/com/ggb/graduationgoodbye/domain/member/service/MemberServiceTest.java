package com.ggb.graduationgoodbye.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ggb.graduationgoodbye.domain.artist.business.ArtistCreator;
import com.ggb.graduationgoodbye.domain.artist.business.ArtistValidator;
import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.commonCode.business.MajorReader;
import com.ggb.graduationgoodbye.domain.commonCode.business.UniversityReader;
import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.member.business.MemberCreator;
import com.ggb.graduationgoodbye.domain.member.business.MemberProvider;
import com.ggb.graduationgoodbye.domain.member.business.MemberReader;
import com.ggb.graduationgoodbye.domain.member.business.MemberUpdater;
import com.ggb.graduationgoodbye.domain.member.business.MemberValidator;
import com.ggb.graduationgoodbye.domain.member.business.NicknameProvider;
import com.ggb.graduationgoodbye.domain.member.business.WithdrawProcessor;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.s3.utils.S3Util;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MemberServiceTest extends ServiceTest {

  @InjectMocks
  private MemberService memberService;
  @Mock
  private NicknameProvider nicknameProvider;
  @Mock
  private MemberProvider memberProvider;
  @Mock
  private MemberCreator memberCreator;
  @Mock
  private MemberUpdater memberUpdater;
  @Mock
  private MemberReader memberReader;
  @Mock
  private MemberValidator memberValidator;
  @Mock
  private WithdrawProcessor withdrawProcessor;
  @Mock
  private ArtistCreator artistCreator;
  @Mock
  private ArtistValidator artistValidator;
  @Mock
  private UniversityReader universityReader;
  @Mock
  private MajorReader majorReader;
  @Mock
  private S3Util s3Util;

  @Test
  void join_성공() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    String email = "test@test.com";
    String profile = "testProfile";
    String nickname = "testNick";
    OAuthUserInfoDto oAuthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId(snsId)
        .email(email)
        .profile(profile)
        .build();
    MemberJoinDto.Request request = MemberJoinDto.Request.builder()
        .nickname(nickname)
        .build();
    Member mockMember = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .email(email)
        .profile(profile)
        .nickname(nickname)
        .build();

    Member member = memberService.join(snsType.name(), oAuthUserInfoDto, request);

    assertNotNull(member);
    assertEquals(mockMember.getSnsType(), member.getSnsType());
    assertEquals(mockMember.getSnsId(), member.getSnsId());
    assertEquals(mockMember.getEmail(), member.getEmail());
    assertEquals(mockMember.getProfile(), member.getProfile());
    assertEquals(mockMember.getNickname(), member.getNickname());
    verify(memberCreator, times(1)).save(any(Member.class));
  }

  @Test
  void withdraw_성공() {
    Long memberId = 1L;
    Member mockMember = Member.builder().build();
    Member mockProcessedMember = Member.builder().build();
    when(memberProvider.getCurrentMemberId()).thenReturn(memberId);
    when(memberReader.findById(memberId)).thenReturn(mockMember);
    when(withdrawProcessor.processToWithdraw(mockMember)).thenReturn(mockProcessedMember);

    memberService.withdraw();

    verify(memberProvider, times(1)).getCurrentMemberId();
    verify(memberReader, times(1)).findById(any(Long.class));
    verify(withdrawProcessor, times(1)).processToWithdraw(any(Member.class));
    verify(memberUpdater, times(1)).updateToWithdraw(any(Member.class));
  }

  @Test
  void promoteArtist_성공() {
    Long memberId = 1L;
    String name = "testName";
    String university = "testUniversity";
    String major = "testMajor";
    String certificateUrl = "testCertificateUrl";
    Member mockMember = Member.builder().build();
    CommonCode mockUniversity = CommonCode.builder().build();
    CommonCode mockMajor = CommonCode.builder().build();
    Artist mockArtist = Artist.builder()
        .memberId(mockMember)
        .universityId(mockUniversity)
        .majorId(mockMajor)
        .name(name)
        .build();
    PromoteArtistDto.Request request = PromoteArtistDto.Request.builder()
        .university(university)
        .major(major)
        .name(name)
        .build();
    MultipartFile certificate = new MockMultipartFile("testCert", new byte[1]);
    when(memberProvider.getCurrentMemberId()).thenReturn(memberId);
    when(memberReader.findById(memberId)).thenReturn(mockMember);
    when(universityReader.findUniversity(university)).thenReturn(mockUniversity);
    when(majorReader.findByMajor(major)).thenReturn(mockMajor);
    when(s3Util.upload(certificate)).thenReturn(certificateUrl);
    when(artistCreator.save(any(Artist.class))).thenReturn(mockArtist);

    Artist artist = memberService.promoteArtist(request, certificate);

    assertNotNull(artist);
    assertEquals(mockArtist.getMemberId(), artist.getMemberId());
    assertEquals(mockArtist.getUniversityId(), artist.getUniversityId());
    assertEquals(mockArtist.getMajorId(), artist.getMajorId());
    assertEquals(mockArtist.getName(), artist.getName());
    verify(artistValidator, times(1)).checkArtistDuplication(any(Long.class));
    verify(artistCreator, times(1)).save(any(Artist.class));
  }

  @Test
  void checkSnsType_성공() {
    String snsType = SnsType.GOOGLE.name();

    memberService.checkSnsType(snsType);

    verify(memberValidator, times(1)).validateSnsType(any(String.class));
  }

  @Test
  void checkNicknameExists_성공() {
    String nickname = "testNick";

    memberService.checkNicknameExists(nickname);

    verify(memberReader, times(1)).checkNicknameExists(any(String.class));
  }

  @Test
  void findBySns_성공() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    SnsDto snsDto = new SnsDto(snsType.name(), snsId);
    Member mockMember = Member.builder().build();
    when(memberReader.findBySns(snsDto)).thenReturn(mockMember);

    Member member = memberService.findBySns(snsDto);

    assertNotNull(member);
    verify(memberReader, times(1)).findBySns(any(SnsDto.class));
  }

  @Test
  void getById_성공() {
    Long memberId = 1L;
    Member mockMember = Member.builder().build();
    when(memberReader.findById(memberId)).thenReturn(mockMember);

    Member member = memberService.getById(memberId);

    assertNotNull(member);
    verify(memberReader, times(1)).findById(any(Long.class));
  }

  @Test
  void serveRandomNicknames_성공() {
    int count = 1;

    memberService.serveRandomNicknames(count);

    verify(nicknameProvider, times(1)).provideRandomNicknames(any(Integer.class));
  }

  @Test
  void getMemberOrAuthException_성공() {
    String snsType = "testSnsType";
    String snsId = "testSnsId";
    String oauthToken = "testOauthToken";
    Member mockMember = Member.builder().build();
    when(memberReader.getMemberOrAuthException(any(String.class), any(String.class),
        any(String.class))).thenReturn(mockMember);

    Member member = memberService.getMemberOrAuthException(snsType, snsId, oauthToken);

    assertNotNull(member);
    verify(memberReader, times(1)).getMemberOrAuthException(any(String.class), any(String.class),
        any(String.class));
  }
}
