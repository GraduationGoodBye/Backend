package com.ggb.graduationgoodbye.domain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class TokenExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(UnAuthenticatedException e){
            writeResponseBody(response,HttpStatus.UNAUTHORIZED.value(),e.getCode(),e.getMessage());
        }catch(ForbiddenException e){
            writeResponseBody(response,HttpStatus.FORBIDDEN.value(),e.getCode(),e.getMessage());
        }
    }

    private void writeResponseBody(HttpServletResponse response, int status, String code, String message)
            throws IOException{
        response.setContentType("application/json");
        response.setStatus(status);
        ApiResponse<?> apiResponse = ApiResponse.error(code, message);
        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}
