package com.ggb.graduationgoodbye.global.config.log;

import com.ggb.graduationgoodbye.global.error.type.ApiErrorType;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;


@Slf4j
public class LogFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            // 요청을 필터에 전달
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // 요청과 응답의 로그를 기록
            printLog(requestWrapper, responseWrapper);
            // 응답 본문 클라이언트로 전송
            responseWrapper.copyBodyToResponse();
        }


    }

    private void printLog(ContentCachingRequestWrapper request,ContentCachingResponseWrapper response) throws IOException {
        String queryString = request.getQueryString();
        log.info("Request : {} uri=[{}] ", request.getMethod(), queryString == null ? request.getRequestURI() : request.getRequestURI() + "?" +queryString);
        log.info("Http Status : {} Response : {}", response.getStatus() ,new String(response.getContentAsByteArray(), response.getCharacterEncoding()));
    }



}