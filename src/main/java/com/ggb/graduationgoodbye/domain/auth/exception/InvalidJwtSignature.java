package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import lombok.Getter;

@Getter
public class InvalidJwtSignature extends UnAuthenticatedException {

    private final String code;

    public InvalidJwtSignature() {
        super(AuthErrorType.INVALID_JWT_SIGNATURE.getMessage());
        this.code = AuthErrorType.INVALID_JWT_SIGNATURE.name();
    }

    public InvalidJwtSignature(String message) {
        super(message);
        this.code = AuthErrorType.INVALID_JWT_SIGNATURE.name();
    }
}
