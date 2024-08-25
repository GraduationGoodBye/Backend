package com.ggb.graduationgoodbye.domain.admin.controller;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 승인 요청 대기 증명서 조회 Response.
 */
@Getter
@Setter
public class FindPendingCertificateResponse {

  private String email;
  private String profile;
  private String memberNickname;
  private String address;
  private String gender;
  private Integer age;
  private String phone;
  private String university;
  private String major;
  private String artistNickname;
  private LocalDateTime promotionRequestTime;

}
