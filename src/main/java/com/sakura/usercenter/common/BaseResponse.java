package com.sakura.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: sakura
 * @date: 2024/7/7 下午11:16
 * @description: 通用返回类
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    private static final long serialVersionUID = 2987398239326222181L;

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, String message, String description) {
        this.code = code;
        this.data = null;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
