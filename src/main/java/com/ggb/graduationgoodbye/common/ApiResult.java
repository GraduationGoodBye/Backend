package com.ggb.graduationgoodbye.common;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ApiResult<T> extends CommonResult {
    private T data;

    ApiResult(){}

    ApiResult(CommonResponse commonResponse){
        setCode(commonResponse.getCode());
        setMessage(commonResponse.getMessage());
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>(CommonResponse.SUCCESS);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> notFound(T data) {
        ApiResult<T> result = new ApiResult<>(CommonResponse.NOT_FOUND);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> notAcceptable(T data) {
        return new ApiResult<>(CommonResponse.NOT_ACCEPTABLE);
    }

    public static <T> ApiResult<T> unsupportedMediaType(T data) {
        return new ApiResult<>(CommonResponse.UNSUPPORTED_MEDIA_TYPE);
    }

    public static <T> ApiResult<T> internalServerError(T data) {
        return new ApiResult<>(CommonResponse.INTERNAL_SERVER_ERROR);
    }

    public static <T> ApiResult<T> serviceUnavailable(T data) {
        return new ApiResult<>(CommonResponse.SERVICE_UNAVAILABLE);
    }

    public static <T> ApiResult<T> badRequest(T data) {
        ApiResult<T> result = new ApiResult<>(CommonResponse.BAD_REQUEST);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> feignConnectFail(T data) {
        return new ApiResult<>(CommonResponse.FEIGN_CONNECT_FAIL);
    }

    public static <T> ApiResult<T> unauthorized(T data) {
        return new ApiResult<>(CommonResponse.UNAUTHORIZED);
    }

    public static <T> ApiResult<T> forbidden(T data) {
        return new ApiResult<>(CommonResponse.FORBIDDEN);
    }

    public static <T> ApiResult<T> expiredRefreshToken(T data) {
        return new ApiResult<>(CommonResponse.EXPIRED_REFRESH_TOKEN);
    }

    public static <T> ApiResult<T> duplicateUser(T data) {
        return new ApiResult<>(CommonResponse.DUPLICATE_USER);
    }

    public static <T> ApiResult<T> sessionExpire(T data) {
        return new ApiResult<>(CommonResponse.SESSION_EXPIRE);
    }

    public static <T> ApiResult<T> fail(T data) {
        return new ApiResult<>(CommonResponse.FAIL);
    }

}
