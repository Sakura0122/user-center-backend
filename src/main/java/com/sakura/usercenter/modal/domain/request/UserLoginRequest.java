package com.sakura.usercenter.modal.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: sakura
 * @date: 2024/6/28 下午11:01
 * @description: 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private static final long serialVersionUID = -2477123258994441931L;
}
