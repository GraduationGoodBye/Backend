package com.ggb.graduationgoodbye.global.error.exception;

import com.ggb.graduationgoodbye.global.error.type.ApiErrorType;

public class ServerException extends RuntimeException {
    private final String code;

    public ServerException() {
        super(ApiErrorType.BAD_REQUEST.getMessage());
        this.code = ApiErrorType.BAD_REQUEST.name();
    }

    public ServerException(final String message) {
        super(message);
        this.code = ApiErrorType.BAD_REQUEST.name();
    }

    public ServerException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
