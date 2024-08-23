package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import lombok.Getter;


/**
 * 작가 전환 요청 DTO.
 */
public class PromoteArtistDto {

  /**
   * 작가 전환 요청 Request.
   */
  @Getter
  public static class Request {

    private String university;
    private String major;
    private String name;
  }

  /**
   * 작가 전환 요청 Response.
   */
  @Getter
  public static class Response {

    // member 정보도 함께 반환 필요
    // private Member member;
    private String university;
    private String major;
    private String name;
    private String certificateUrl;

    Response(Artist artist) {
      this.university = artist.getUniversityId().getName();
      this.major = artist.getMajorId().getName();
      this.name = artist.getName();
      this.certificateUrl = artist.getCertificateUrl();
    }
  }
}
