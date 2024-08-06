package com.ggb.graduationgoodbye.domain.member.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends BusinessException {

    private final String code;

    public MemberNotFoundException(){
        super(MemberErrorType.MEMBER_NOT_FOUND.getMessage());
        this.code = MemberErrorType.MEMBER_NOT_FOUND.name();
    }

    public MemberNotFoundException(final String message) {
        super(message);
        this.code = MemberErrorType.MEMBER_NOT_FOUND.name();
    }
}
