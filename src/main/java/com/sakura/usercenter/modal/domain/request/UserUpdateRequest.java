package com.sakura.usercenter.modal.domain.request;

import lombok.Data;

/**
 * @author: sakura
 * @date: 2024/7/8 下午11:22
 * @description:
 */
@Data
public class UserUpdateRequest {
    private Long id;
    private String username;
    private String avatarUrl;
    private String phone;
    private String email;
}
