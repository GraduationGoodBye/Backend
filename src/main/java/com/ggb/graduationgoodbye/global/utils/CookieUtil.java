package com.ggb.graduationgoodbye.global.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

  public static ResponseCookie createCookie(String name, String value) {
    return ResponseCookie.from(name, value)
        .path("/")
        .httpOnly(true)
        .secure(false)
//        .sameSite("Strict")
        .maxAge(3600)
        .build();
  }

  public static void addCookie(HttpServletResponse response, ResponseCookie cookie) {
    response.addHeader("Set-Cookie", cookie.toString());
  }
}
