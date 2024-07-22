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
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


@Slf4j
public class LogFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        LocalDateTime startTime = LocalDateTime.now();

        try {
            // 요청을 필터에 전달
            filterChain.doFilter(requestWrapper, responseWrapper);
            logRequest(requestWrapper,startTime);
        } finally {

            LocalDateTime endTime = LocalDateTime.now();
            Duration responseTime = Duration.between(startTime, endTime);

            logResponse(responseWrapper,responseTime);
            // 응답 본문 클라이언트로 전송
            responseWrapper.copyBodyToResponse();
        }


    }


    private void logRequest(ContentCachingRequestWrapper request,LocalDateTime time) {

        String parameter = request.getQueryString();

        log.info("[Request.{}] Method : {} uri={} body : {}"
                , time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                , request.getMethod()
                , parameter == null ? request.getRequestURI() : request.getRequestURI() + "?" +parameter
                , getBody(request));
    }

    public String getBody(ContentCachingRequestWrapper request){
        byte[] body = request.getContentAsByteArray();
        return new String(body, StandardCharsets.UTF_8);
    }

    private void logResponse(ContentCachingResponseWrapper response,Duration time) throws IOException {
        log.info("[Response. {}ms] Http Status : {} body : {}"
                , time.toMillis()
                , response.getStatus()
                ,new String(response.getContentAsByteArray()
                        , response.getCharacterEncoding()));
    }



}