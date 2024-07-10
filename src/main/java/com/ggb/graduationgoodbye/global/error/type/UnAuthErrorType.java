package com.ggb.graduationgoodbye.global.error.type;

import lombok.Getter;

@Getter
public enum UnAuthErrorType {

    EXPIRED_TOKEN ("만료된 토큰입니다."),
    INVALID_TOKEN ("유효하지 않은 토큰입니다.");

    private final String message;

    UnAuthErrorType(String message) {
        this.message = message;
    }
}
