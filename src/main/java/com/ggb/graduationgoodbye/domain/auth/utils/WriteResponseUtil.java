package com.ggb.graduationgoodbye.domain.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WriteResponseUtil {

    public <T> void writeResponse(HttpServletResponse response, int status, ApiResponse apiResponse)
            throws IOException{
        response.setContentType("application/json");
        response.setStatus(status);
        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }

}
