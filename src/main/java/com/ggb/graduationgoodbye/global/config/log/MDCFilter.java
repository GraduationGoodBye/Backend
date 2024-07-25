package com.ggb.graduationgoodbye.global.config.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;


import java.io.IOException;
import java.util.UUID;


public class MDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        MDC.put("uuid", uuid);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("uuid");
        }
    }
}