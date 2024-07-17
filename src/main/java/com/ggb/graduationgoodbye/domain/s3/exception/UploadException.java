package com.ggb.graduationgoodbye.domain.s3.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class UploadException extends ServerException {
    /**
     *  note : Internal_Server_Exception 로 처리되는 Exception 이 정의되어 있지 않아서 - ServerException 추가함.
     *  note : IOException 은 500으로 처리되는 것이 논리적으로 맞기 때문.
     */
    private final String code;

    public UploadException() {
        super(S3ErrorType.UPLOAD_FAIL.name());
        this.code = S3ErrorType.UPLOAD_FAIL.getMessage();
    }

    public UploadException(String message) {
        super(message);
        this.code = S3ErrorType.UPLOAD_FAIL.name();
    }
}
