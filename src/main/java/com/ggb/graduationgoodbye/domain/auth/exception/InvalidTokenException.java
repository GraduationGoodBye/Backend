package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.ApiErrorType;
import com.ggb.graduationgoodbye.global.error.exception.UnauthenticatedException;
import lombok.Getter;

import java.io.Serial;

@Getter
public class InvalidTokenException extends UnauthenticatedException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    public InvalidTokenException() {
        super(ApiErrorType.INVALID_TOKEN.getMessage());
        this.code = ApiErrorType.INVALID_TOKEN.name();
    }

    public InvalidTokenException(String message) {
        super(message);
        this.code = ApiErrorType.INVALID_TOKEN.name();
    }
}
