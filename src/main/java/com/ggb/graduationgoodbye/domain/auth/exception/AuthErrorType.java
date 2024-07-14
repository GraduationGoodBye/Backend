package com.ggb.graduationgoodbye.domain.auth.exception;

import lombok.Getter;

@Getter
public enum AuthErrorType {

    EXPIRED_TOKEN ("만료된 토큰입니다."),
    INVALID_TOKEN ("유효하지 않은 토큰입니다.");

    private final String message;

    AuthErrorType(String message) {
        this.message = message;
    }
}
