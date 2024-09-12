package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.domain.auth.common.exception.ExpiredTokenException;
import com.ggb.graduationgoodbye.domain.auth.common.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.test.service.TestService;
import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController implements TestApi {

  private final TestService testService;

  @Override
  @GetMapping("/check")
  public ApiResponse<?> check() {
    return ApiResponse.ok("This service is available");
  }

  @Override
  @GetMapping("/exception/{name}")
  public void exception(@PathVariable String name) {
    switch (name) {
      case "UNAUTHENTICATED" -> throw new UnAuthenticatedException();
      case "FORBIDDEN" -> throw new ForbiddenException();
      case "BAD_REQUEST" -> throw new BusinessException();
      case "EXPIRED_TOKEN" -> throw new ExpiredTokenException();
      case "INVALID_TOKEN" -> throw new InvalidTokenException();
      case "INTERNAL_SERVER_ERROR" -> throw new RuntimeException("INTERNAL_SERVER_ERROR");
      default -> throw new RuntimeException("default");
    }
  }

  @Override
  @PostMapping("/image")
  public ApiResponse<?> image(@RequestPart(value = "file") MultipartFile file) {
    String url = testService.uploadImageTest(file);
    return ApiResponse.ok(url);
  }

  @Override
  @PostMapping("/base64/encode")
  public ApiResponse<?> encode(@RequestBody String data) {
    String encodedData = testService.encode(data);
    return ApiResponse.ok(encodedData);
  }

  @Override
  @PostMapping("/base64/decode")
  public ApiResponse<?> decode(@RequestBody String data) {
    String plainText = testService.decode(data);
    return ApiResponse.ok(plainText);
  }

  @Override
  @GetMapping("/auth/member")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiResponse<?> authMember() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("name", SecurityContextHolder.getContext().getAuthentication().getName());
    map.put("principal",
        SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    map.put("authorities",
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    return ApiResponse.ok(map);
  }

  @Override
  @GetMapping("/auth/admin")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ApiResponse<?> authAdmin() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("name", SecurityContextHolder.getContext().getAuthentication().getName());
    map.put("principal",
        SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    map.put("authorities",
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    return ApiResponse.ok(map);
  }
}
