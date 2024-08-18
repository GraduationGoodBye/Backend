package com.ggb.graduationgoodbye.domain.commonCode.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class NotFoundMajorException extends BusinessException {

    private final String code;

    public NotFoundMajorException() {
        super(CommonCodeErrorType.NOT_FOUND_MAJOR.getMessage());
        this.code = CommonCodeErrorType.NOT_FOUND_MAJOR.name();
    }

    public NotFoundMajorException(final String message) {
        super(message);
        this.code = CommonCodeErrorType.NOT_FOUND_MAJOR.name();;
    }
}
