package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "test", description = "the function test API")
public interface TestApi {

  @Operation(summary = "연결 확인", tags = {"test"})
  ApiResponse<?> check();

  @Operation(summary = "예외 로그", tags = {"test"})
  void exception(
      @Parameter(in = ParameterIn.PATH, schema = @Schema(type = "string", allowableValues = {
          "UNAUTHENTICATED", "FORBIDDEN", "BAD_REQUEST", "EXPIRED_TOKEN", "INVALID_TOKEN",
          "INTERNAL_SERVER_ERROR"})
      ) String name);

  @Operation(summary = "이미지 업로드", tags = {"test"})
  ApiResponse<?> image(
      @Parameter MultipartFile file);

  @Operation(summary = "base64 인코딩", tags = {"test"})
  ApiResponse<?> encode(@RequestBody String data);

  @Operation(summary = "base64 디코딩", tags = {"test"})
  ApiResponse<?> decode(@RequestBody String data);

  @Operation(summary = "회원 인증", tags = {"test"})
  ApiResponse<?> authMember();

  @Operation(summary = "관리자 인증", tags = {"test"})
  ApiResponse<?> authAdmin();
}
