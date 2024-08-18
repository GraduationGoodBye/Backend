package com.ggb.graduationgoodbye.domain.commonCode.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class NotFoundUniversityException extends BusinessException {

    private final String code;

    public NotFoundUniversityException() {
        super(CommonCodeErrorType.NOT_FOUND_UNIVERSITY.getMessage());
        this.code = CommonCodeErrorType.NOT_FOUND_UNIVERSITY.name();
    }

    public NotFoundUniversityException(final String message) {
        super(message);
        this.code = CommonCodeErrorType.NOT_FOUND_UNIVERSITY.name();;
    }
}
