package com.ggb.graduationgoodbye.domain.auth.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class NotJoinedUserException extends AuthenticationException {

    private final String code;
    private final String accessToken;

    public NotJoinedUserException(String accessToken){
        this(AuthErrorType.NOT_JOINED_USER.getMessage(), accessToken);
    }

    public NotJoinedUserException(String message, String accessToken){
        super(message);
        this.accessToken = accessToken;
        this.code = AuthErrorType.NOT_JOINED_USER.name();
    }
}
