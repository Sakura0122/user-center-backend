package com.sakura.usercenter.service;

import com.sakura.usercenter.modal.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author sakura
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-27 21:53:47
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
