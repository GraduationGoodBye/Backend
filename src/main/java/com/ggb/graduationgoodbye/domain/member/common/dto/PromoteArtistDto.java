package com.ggb.graduationgoodbye.domain.member.common.dto;

import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


/**
 * 작가 전환 요청 DTO.
 */
public class PromoteArtistDto {

  /**
   * 작가 전환 요청 Request.
   */
  @Schema(name = "PromoteArtist_Request", description = "작가 전환 요청 DTO")
  @Builder
  @Getter
  public static class Request {

    @Schema(description = "대학교")
    private String university;
    @Schema(description = "학과")
    private String major;
    @Schema(description = "작가활동명")
    private String name;
  }

  /**
   * 작가 전환 요청 Response.
   */
  @Schema(name = "PromoteArtist_Response", description = "작가 전환 응답 DTO")
  @Getter
  public static class Response {

    // member 정보도 함께 반환 필요
    // private Member member;
    @Schema(description = "대학교")
    private String university;
    @Schema(description = "학부")
    private String major;
    @Schema(description = "작가활동명")
    private String name;
    @Schema(description = "졸업증명서 URL")
    private String certificateUrl;

    public Response(Artist artist) {
      this.university = artist.getUniversityId().getName();
      this.major = artist.getMajorId().getName();
      this.name = artist.getName();
      this.certificateUrl = artist.getCertificateUrl();
    }
  }
}
