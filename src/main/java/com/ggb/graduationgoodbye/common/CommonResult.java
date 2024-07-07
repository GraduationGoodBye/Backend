package com.ggb.graduationgoodbye.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
    private boolean success = false;
    private String code;
    private String message;

    @Getter
    public enum CommonResponse {
        SUCCESS("200", "정상"),
        NOT_FOUND("404", "해당 데이터를 찾을 수 없습니다."),
        NOT_ACCEPTABLE("406", "허용되지 않는 요청입니다."),
        UNSUPPORTED_MEDIA_TYPE("415", "지원되지 않는 미디어 타입입니다."),
        INTERNAL_SERVER_ERROR("500", "서버 내부 오류가 발생했습니다."),
        SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다."),
        BAD_REQUEST("4000", "검증 오류"),
        FEIGN_CONNECT_FAIL("5010", "서버 연동 중 오류가 발생했습니다."),
        UNAUTHORIZED("9401", "인증되지 않은 사용자"),
        FORBIDDEN("9403", "권한없음"),
        EXPIRED_REFRESH_TOKEN("9499", "리프레쉬 토큰 만료"),
        DUPLICATE_USER("9777", "중복로그인"),
        SESSION_EXPIRE("9790", "사용자 세션 만료"),
        FAIL("9999", "알 수 없는 오류가 발생했습니다.");

        private final String code;
        private final String message;

        CommonResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
