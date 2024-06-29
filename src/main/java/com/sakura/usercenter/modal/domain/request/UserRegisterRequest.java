package com.sakura.usercenter.modal.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: sakura
 * @date: 2024/6/28 下午11:01
 * @description: 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private static final long serialVersionUID = 5811378926847144885L;
}
