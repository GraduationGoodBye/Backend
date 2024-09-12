package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.member.common.dto.MemberInfoDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.TokenReissueDto;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "member", description = "the member API")
public interface MemberApi {

  @Operation(summary = "로그인", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<MemberLoginDto.Response> login(
      @Parameter String snsType,
      @RequestBody MemberLoginDto.Request request);


  @Operation(summary = "회원가입", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터 이상"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<MemberJoinDto.Response> signup(
      @Parameter String snsType,
      @RequestBody MemberJoinDto.Request request);

  @Operation(summary = "회원탈퇴", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<?> withdraw();

  @Operation(summary = "회원정보 조회", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<MemberInfoDto.Response> info();

  @Operation(summary = "액세스 토큰 재발급", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터 이상"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<TokenReissueDto.Response> reissue(@RequestBody TokenReissueDto.Request request);

  @Operation(summary = "닉네임 중복확인", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 중복확인 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터 이상"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<?> checkNickname(@Parameter String nickname);

  @Operation(summary = "닉네임 랜덤 생성", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 랜덤 생성 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터 이상"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<?> serveRandomNicknames(@Parameter int count);

  @Operation(summary = "작가 등업 신청", tags = {"member"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작가 등업 신청 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터 이상"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<PromoteArtistDto.Response> promoteArtist(
      @Parameter MultipartFile certificate,
      @RequestBody PromoteArtistDto.Request request);
}
