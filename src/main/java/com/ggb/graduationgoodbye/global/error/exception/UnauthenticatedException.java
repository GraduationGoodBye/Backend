package com.ggb.graduationgoodbye.global.error.exception;

import com.ggb.graduationgoodbye.global.error.ApiErrorType;
import lombok.Getter;

@Getter
public class UnauthenticatedException extends RuntimeException {

    private final String code;

    public UnauthenticatedException() {
        super(ApiErrorType.UNAUTHENTICATED.getMessage());
        this.code = ApiErrorType.UNAUTHENTICATED.name();
    }

    public UnauthenticatedException(final String message) {
        super(message);
        this.code = ApiErrorType.UNAUTHENTICATED.name();
    }

    public UnauthenticatedException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
