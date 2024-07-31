package com.ggb.graduationgoodbye.domain.auth.filter;

import com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
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

    private final WriteResponseUtil writeResponseUtil;

    public TokenExceptionHandlingFilter(WriteResponseUtil writeResponseUtil) {
        this.writeResponseUtil = writeResponseUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(UnAuthenticatedException e){
            writeResponseUtil.writeResponse(response,HttpStatus.UNAUTHORIZED.value(),e.getCode(),e.getMessage());
        }catch(ForbiddenException e){
            writeResponseUtil.writeResponse(response,HttpStatus.FORBIDDEN.value(),e.getCode(),e.getMessage());
        }
    }
}
