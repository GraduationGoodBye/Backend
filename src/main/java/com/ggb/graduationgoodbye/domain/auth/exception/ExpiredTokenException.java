package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ExpiredTokenException extends UnAuthenticatedException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    public ExpiredTokenException() {
        super(AuthErrorType.EXPIRED_TOKEN.name());
        this.code = AuthErrorType.EXPIRED_TOKEN.getMessage();
    }

    public ExpiredTokenException(String message) {
        super(message);
        this.code = AuthErrorType.EXPIRED_TOKEN.name();
    }
}
