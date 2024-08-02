package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;

public class NoTokenException extends UnAuthenticatedException {
    private final String code;

    public NoTokenException(){
        super(AuthErrorType.NO_TOKEN.getMessage());
        this.code = AuthErrorType.NO_TOKEN.name();
    }

    public NoTokenException(final String message){
        super(message);
        this.code = AuthErrorType.NO_TOKEN.name();
    }
}
