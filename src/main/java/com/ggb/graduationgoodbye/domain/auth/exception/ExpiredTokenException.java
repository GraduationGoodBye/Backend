package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.ApiErrorType;
import com.ggb.graduationgoodbye.global.error.exception.UnauthenticatedException;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ExpiredTokenException extends UnauthenticatedException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    public ExpiredTokenException() {
        super(ApiErrorType.EXPIRED_TOKEN.name());
        this.code = ApiErrorType.EXPIRED_TOKEN.getMessage();
    }

    public ExpiredTokenException(String message) {
        super(message);
        this.code = ApiErrorType.EXPIRED_TOKEN.name();
    }
}
