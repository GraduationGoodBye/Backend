package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;

public class NotFoundTokenException extends UnAuthenticatedException {
    private final String code;

    public NotFoundTokenException() {
        super(AuthErrorType.NOT_FOUND_TOKEN.getMessage());
        this.code = AuthErrorType.NOT_FOUND_TOKEN.name();
    }

    public NotFoundTokenException(final String message) {
        super(message);
        this.code = AuthErrorType.NOT_FOUND_TOKEN.name();
    }
}
