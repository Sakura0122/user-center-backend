package com.sakura.usercenter.exception;

import com.sakura.usercenter.common.ErrorCode;
import lombok.Getter;

/**
 * @author: sakura
 * @date: 2024/7/8 下午9:36
 * @description: 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -92496825884151516L;
    private final int code;
    private final String description;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.description = "";
    }

    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
}
