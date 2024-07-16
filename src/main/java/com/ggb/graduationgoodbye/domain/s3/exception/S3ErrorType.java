package com.ggb.graduationgoodbye.domain.s3.exception;

import lombok.Getter;

@Getter
public enum S3ErrorType {

    UPLOAD_FAIL("업로드에 실패했습니다.");

    private final String message;

    S3ErrorType(String message) {this.message = message;}
}
