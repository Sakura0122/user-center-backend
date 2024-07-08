package com.sakura.usercenter.common;

/**
 * @author: sakura
 * @date: 2024/7/7 下午11:24
 * @description: 返回结果工具类
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), description);
    }
}
