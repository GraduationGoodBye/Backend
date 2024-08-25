package com.ggb.graduationgoodbye.domain.auth.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class WriteResponseUtil {

  public <T> void writeResponse(HttpServletResponse response, int status,
      ApiResponse<T> apiResponse)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(status);
    new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
  }
}
