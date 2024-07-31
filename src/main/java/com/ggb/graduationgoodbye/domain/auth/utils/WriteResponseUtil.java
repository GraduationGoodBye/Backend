package com.ggb.graduationgoodbye.domain.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WriteResponseUtil {
    public void writeResponse(HttpServletResponse response, int status, String code, String message)
            throws IOException {
        writeResponse(response, status, code, message, null);
    }

    public <T> void writeResponse(HttpServletResponse response, int status, String code, String message, T data)
            throws IOException{
        response.setContentType("application/json");
        response.setStatus(status);
        ApiResponse<?> apiResponse = ApiResponse.error(code, message, data);
        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }

}
